import { createInstance } from "./index.js";

const instance = createInstance();

function listBoard(body, response, fail) {
  instance.get("/donationBoard/listBoard").then(response).catch(fail);
}

function getDonationBoard(success, fail) {
  instance
    .get("/donationBoard/urgentBoard")
    .then(function (response) {
      success(response);
    })
    .catch(fail);
}
export { listBoard, getDonationBoard };
