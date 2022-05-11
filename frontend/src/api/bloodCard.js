import { tokenInstance } from "./tokenIndex.js";

const instance = tokenInstance();

function findByBloodCard(success, fail) {
  instance
    .get("api/bc/search")
    .then(function (response) {
      success(response);
    })
    .catch(fail);
}

function bloodCardChageState(
  bloodCardId,
  transactionCardMessage,
  transactionCardToId,
  success,
  fail
) {
  const body = {
    bloodCardId: bloodCardId,
    transactionCardMessage: transactionCardMessage,
    transactionCardToId: transactionCardToId,
  };
  instance
    .post("api/bc/donation", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function createBloodCard(body, success, fail) {
  instance
    .post("api/bc/create", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function thSendBloodCard(success, fail) {
  instance.get("api/bc/transactionHistory/send").then(success).catch(fail);
}

export {
  findByBloodCard,
  bloodCardChageState,
  createBloodCard,
  thSendBloodCard,
};
