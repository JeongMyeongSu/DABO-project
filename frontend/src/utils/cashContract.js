import Web3 from 'web3';
import { BLOCKCHAIN_URL,  CASH_CONTRACT_ADDRESS } from '@/config';
import { CASH_CONTRACT_ABI } from '@/config/ABIs.js';

function createCashContract(web3) {
  return new web3.eth.Contract(CASH_CONTRACT_ABI, CASH_CONTRACT_ADDRESS);
}
/**
 * [캐시 충전]
 * @param {walletAddress, amount} options 
 */
export function buyCash(amount, privateKey, onConfirm, onFail) {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createCashContract(web3);
  var buyCall = contract.methods.buy();
  var encodedABI = buyCall.encodeABI();
  var tx = {
    to: CASH_CONTRACT_ADDRESS,
    gas: '200000', //gwei
    value: amount,
    data: encodedABI,
  };
  
  web3.eth.accounts.signTransaction(tx, privateKey).then(signed => {
    web3.eth
      .sendSignedTransaction(signed.rawTransaction)
      .then(onConfirm)
  }).catch(err => {
    onFail(err);
  });
}

/**
 * [토큰 전송]
 * 구매자가 에스크로에 돈 입금
 * 스마트 컨트랙트의 Transfer 이벤트를 리스닝하고 있다가 완료 또는 실패 시 이벤트 메서드를 호출
 * @param {escrowAddress, amount} options 
 */
export function leaveDeposit(options, walletAddress, privateKey, onConfirm, onFail) {
  // var web3 = new Web3(
  //   new Web3.providers.WebsocketProvider(BLOCKCHAIN_WEBSOCKET_URL),
  // );

  // var contract = createCashContract(web3);
  // // 캐시 전송 완료 이벤트 리스닝
  // contract.events.Transfer({
  //   filter: {from: [ walletAddress ]}
  // }, (error, data) => {
  //   if (error) {
  //     onFail(error);
  //   } else {
  //     onConfirm(data);
  //   }
  // });

  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createCashContract(web3);
  var transferCall = contract.methods.transfer(
    options.escrowAddress,
    options.amount
  );

  var encodedABI = transferCall.encodeABI();
  var tx = {
    from: walletAddress,
    to: CASH_CONTRACT_ADDRESS,
    gas: '200000', //gwei
    data: encodedABI,
  };

  web3.eth.accounts.signTransaction(tx, privateKey).then(signed => {
    web3.eth
      .sendSignedTransaction(signed.rawTransaction)
      .then(onConfirm)
  }).catch(err => {
    onFail(err);
  });
}

/**
* [토큰 잔액 조회]
* 스마트 컨트랙트의 잔액 확인 함수 호출
*/
export function getBalance(walletAddress, onConfirm, onFail) {
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createCashContract(web3);
  contract.methods
    .balanceOf(walletAddress)
    .call()
    .then(onConfirm)
    .catch(onFail);
}