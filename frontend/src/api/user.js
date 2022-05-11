import { loginInstance } from "./index.js";

const instance = loginInstance();
function signup(body, success, fail) {
    

    instance
      .post("/api/user/signUp", JSON.stringify(body))
      .then(success)
      .catch(fail);
  }

function loginAPI(body, success, fail) {
  instance
    .post("/api/user/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}
function getUserInfo( success, fail){
    const accessToken = localStorage.getItem("accessToken");
    console.log(accessToken)
    instance
    .get("/api/user/me", {
      headers: {
        Authorization:  `Bearer ${accessToken}`
      }
    }).then(success)
    .catch(fail);
}


export { loginAPI,getUserInfo,signup };
