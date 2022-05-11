import axios from "axios";
import Vue from "vue";
import Vuex from "vuex";
//import jwt_decode from "jwt-decode";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    accessToken: localStorage.getItem("accessToken"), // 토큰정보
    userInfo: null,
    isSigned: false, // 로그인 여부
    user: {
      id: 0, // 사용자 아이디 저장
      nickname: "",
      walletAddress: null,
    },
    wallet: {
      id: 0,
      ownerId: null,
      address: "",
      balance: 0,
      payBalance: 0,
      cash: 0,
      receivingCount: 0,
    },
  },
  getters: {
    config: function (state) {
      return {
        Authorization: `JWT ${state.accessToken}`,
      };
    },
    // 로그인 했는지 확인
    isLogin: function (state) {
      return !!state.accessToken;
    },
    getAccessToken: function (state) {
      return state.accessToken;
    },
    getUserNumber: function (state) {
      return state.userInfo.userNumber;
    },
    getUserInfo: function (state) {
      return state.userInfo;
    },
    getUserNickName: function () {
      return localStorage.getItem("nickName");
    },
  },
  mutations: {
    SET_LOGIN: function (state, accessToken) {
      axios({
        method: "get",
        url: `http://localhost:8080/api/user/me`,
        headers: {
          Authorization: `Bearer ` + accessToken,
        },
      }).then((response) => {
        state.userInfo = response.data;
      });
      //let decode_token = jwt_decode(accessToken);

      //console.log(decode_token);
      //state.userInfo = decode_token.loginUser;

      state.accessToken = accessToken;
      state.isLogin = true;

      localStorage.setItem("accessToken", accessToken);
    },
    SET_LOGOUT: function (state) {
      state.accessToken = null;
      state.isLogin = false;
      state.myuserNumber = null;
      state.userInfo = null;
      localStorage.clear();

      location.reload();
    },
    SET_NICKNAME: function (state, nickname) {
      state.userInfo.userNickName = nickname;
    },
    setIsSigned(state, isSigned) {
      state.isSigned = isSigned;
    },
    setUserId(state, id) {
      state.user.id = id;
    },
    setUserNickName(state, nickname) {
      state.user.nickname = nickname;
    },
    setWalletAddress(state, address) {
      state.user.walletAddress = address;
    },
    setWallet(state, wallet) {
      state.wallet = wallet;
    },
    logout(state) {
      state.isSigned = false;
      state.user.id = 0;
      state.user.walletAddress = null;
    },
  },
  actions: {
    loginGetToken: function ({ commit }, token) {
      commit("SET_LOGIN", token);
    },
    logoutRemoveToekn: function ({ commit }) {
      commit("SET_LOGOUT");
    },
    nicknameRenew({ commit }, nickname) {
      commit("SET_NICKNAME", nickname);
    },
  },
  modules: {},
});
