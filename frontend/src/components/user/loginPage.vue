<template>
  <div class="login-page">
    <p class="header">Log In</p>

    <div class="submit-form">
      <input
        v-model="userData.email"
        type="email"
        name="email"
        placeholder="Email"
      />
      <input
        v-model="userData.password"
        type="password"
        name="password"
        placeholder="Password"
      />
      <div class="btn_findpw">
        <button @click="$router.push({ name: 'findpassword' })">
          Forget yout password?
        </button>
      </div>
      <button @click="login()" class="btn_red">
        <span>Login</span>
      </button>
      <!-- <button class="btn_social">
        <img src="@/assets/kakao_login_medium_wide.png" />
      </button> -->
      <button @click="$router.push({ name: 'signup' })" class="btn_red">
        <span>SignUp</span>
      </button>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
import { mapActions } from "vuex";
// import { API_BASE_URL } from "@/config";
import { loginAPI, getUserInfo } from "../../api/user.js";
import { findByUserId as findWallet } from "../../api/wallet.js";

export default {
  data: function () {
    return {
      userData: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    ...mapActions(["loginGetToken"]),
    login() {
      console.log("로그인 실행");
      if (this.userData.email === "") {
        alert("아이디 미입력");
        return false;
      } else if (this.userData.password === "") {
        alert("패스워드 미입력");
        return false;
      }

      console.log("loginAPI START");
      const scope = this;

      loginAPI(
        this.userData,
        function (response) {
          console.log(response);
          scope.$store.commit("setIsSigned", true);
          
          alert("로그인 성공");
          scope.$emit("login");
          // this.loginGetToken(response.data.accessToken);
          localStorage.setItem("accessToken", response.data.accessToken);
          console.log("유저 정보를 찾습니다");
          getUserInfo(
            function (response) {
              console.log("getUserInfo",response);
              scope.$store.commit("setUserId", response.data.userId);
              scope.$store.commit("setUserNickName", response.data.nickname);
              alert("지갑 정보를 찾습니다");
              findWallet(
                response.data.userId,
                function (response) {
                  if (response.status == 200) {
                    console.log("지갑 정보 찾기 성공")
                    console.log(response)
                    localStorage.setItem("walletAddress", response.data.address);
                    scope.$store.commit(
                      "setWalletAddress",
                      response.data.address
                    );
                    scope.$store.commit("setWallet", response.data)
                  } else {
                    alert("Unexpected status code: " + response.status);
                  }
                },
                function (err) {
                  if (err.response != 404) {
                    console.error(err);
                    alert("지갑 정보를 찾지 못했습니다.");
                  }
                }
              );
            },
            function (err) {
              if (err.response != 404) {
                console.error(err);
                alert("유저 정보를 찾지 못했습니다.");
              }
            }
          );

          scope.$router.push({ name: "home" });
        },
        function (error) {
          console.error(error);
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );

      // axios({
      //   method: "post",
      //   url: API_BASE_URL + "/api/user/login",
      //   data: this.userData,
      // })
      //   .then((res) => {
      //     if (res.status == 200) {
      //       console.log("로그인 성공");
      //       alert("로그인 성공");
      //       this.$emit("login");
      //       this.loginGetToken(res.data.accessToken);
      //       this.$router.push({ name: "home" });
      //     } else {
      //       alert(res.data.message);
      //     }
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //     alert("로그인 실패");
      //   });
    },
  },
};
</script>

<style scoped>
.login-page {
  text-align: -webkit-center;
  margin: 0 auto;
  width: 70%;
  font-weight: bold;
}
.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  margin: 1%;
  width: 100%;
  height: 35px;
  font-size: 12px;
  font-weight: bold;
}
.btn_social {
  background-color: #fee500;
  color: #fff;
  border-radius: 10px;
  border: none;
  margin: 1%;
  width: 100%;
  height: 35px;
  font-size: 12px;
  font-weight: bold;
}
.btn_social img {
  height: -webkit-fill-available;
}
.btn_findpw {
  float: right;
  margin-bottom: 10%;
}
.btn_findpw button {
  border: none;
  background: none;
  font-size: 12px;
}
.header {
  margin-top: 10%;
  margin-bottom: 10%;
  font-size: x-large;
  font-family: "NicoMoji" !important;
}
.submit-form input {
  width: 100%;
  height: 35px;
  margin-top: 2%;
  margin-bottom: 2%;
  border-radius: 8px;
  background-color: #f6f6f6;
  border: 1px solid #e8e8e8;
}
.submit-form input::placeholder {
  color: #bdbdbd;
  font-size: 12px;
  padding: 10px;
}
</style>
