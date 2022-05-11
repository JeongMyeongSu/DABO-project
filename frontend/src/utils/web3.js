import Web3 from 'web3';
import {
  BLOCKCHAIN_URL,
//   BLOCKCHAIN_WEBSOCKET_URL,
//   ITEM_INVENTORY_CONTRACT_ADDRESS,
} from '../config';
// Web3 Object 생성
export function createWeb3() {
    var web3 = new Web3(new Web3.providers.HttpProvider(BLOCKCHAIN_URL));
    return web3;
  }