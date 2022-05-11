const userStore = {
  state: {
    userId: "",
    userName: "",
    token: "",
    walletAddress: null
  },
  mutations: {
    login: function (state, payload) {
      state.userId = payload.userId;
      state.userName = payload.userName;
      state.token = payload.token;
    },
    setWalletAddress(state, address) {
      state.walletAddress = address;
    },
    loginCheck: function (state) {
      if (!state.token) {
        // eslint-disable-next-line no-undef
        router
          .push({
            name: "login",
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
};
export default userStore;
