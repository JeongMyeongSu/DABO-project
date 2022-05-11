import Web3 from 'web3';
import {BLOCKCHAIN_URL,ITEM_INVENTORY_CONTRACT_ADDRESS} from '../config';
import {ITEM_INVENTORY_CONTRACT_ABI,ESCROW_CONTRACT_ABI,} from '../config/ABIs.js';

export function createWeb3() {
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    return web3;
}

export function createFactoryContract(web3) {
    var inventoryContract = new web3.eth.Contract(
     ITEM_INVENTORY_CONTRACT_ABI,
     ITEM_INVENTORY_CONTRACT_ADDRESS,
    );
    return inventoryContract;
}

  // Escrow 컨트랙트의 인스턴스
export function createEscrowContract(web3, contractAddress) {
    var escrowContract = new web3.eth.Contract(
      ESCROW_CONTRACT_ABI,
      contractAddress,
    );
    return escrowContract;
}

export function bloodCardSend(
    escrowContractAddress,
    privateKey,
    success,
    fail,
){
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    var contract = createEscrowContract(web3, escrowContractAddress);
    var sendCall = contract.methods.send();
    var encodedABI = sendCall.encodeABI();
    var tx = {
    to: escrowContractAddress,
    gas: 2000000,
    data: encodedABI,
    };
    web3.eth.accounts
    .signTransaction(tx, privateKey)
    .then(signed => {
      web3.eth
        .sendSignedTransaction(signed.rawTransaction)
        .then(success)
        .catch(fail);
    })
    .catch(fail);
}

export function bloodCardRegister(
  bloodCardId,
  privateKey,
  success,
  fail
){
  var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
  var contract = createFactoryContract(web3);
  var bloodCardCall = contract.methods.registerItem(
    bloodCardId,
    '0',
  );

  var encodedABI = bloodCardCall.encodeABI();
  var tx = {
    to: ITEM_INVENTORY_CONTRACT_ADDRESS,
    gas: 2000000,
    data: encodedABI,
  };

  web3.eth.accounts
    .signTransaction(tx, privateKey)
    .then(signed => {
      web3.eth.sendSignedTransaction(signed.rawTransaction)
        .then(success)
        .catch(fail);
    })
    .catch((error) => {
      if (fail) {
        fail(error);
      } else {
        console.error(error);
      }
    });
}