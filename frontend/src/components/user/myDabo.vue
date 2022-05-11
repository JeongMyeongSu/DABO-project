<template>
  <div class="main-page">
    <div class="main-header">
      <div class="d-flex header-contents">
        <div class="greeting">
          <p style="font-family: 'NicoMoji' !important">My DABO</p>
        </div>
      </div>
      <div id="wrapper">
        <div class="circle">O</div>
      </div>
    </div>

    <div class="contents">
      <div class="div-wallet">
        <div class="contents-title d-flex">
          <div><p>내 지갑</p></div>
          <div class="d-flex">
            <button @click="toCharge()">충전하기</button>
          </div>
        </div>
        <div class="contents-wallet d-flex">
          <div>
            <span>DABO Token 보유 현황</span><br />
            <span style="font-weight: lighter"
              >{{ $store.state.wallet.cash }} DABO</span
            ><br />
            <span>내 지갑 주소</span><br />
            <span style="font-weight: lighter">{{
              $store.state.user.walletAddress
            }}</span>
          </div>
        </div>
        <div class="detail-show">
          <button @click="toWallet()">자세히 보기</button>
        </div>
      </div>
      <div class="div-donation">
        <div class="contents-title">
          <div><p>기부 / 모금한 내역</p></div>
        </div>
        <div class="contents-donation d-flex">
          <div>
            <div class="donation-title">
              <span>기부한 내역 (총 N회 / N개)</span>
            </div>
            <div class="donation-summary">
              <table>
                <th>기부 일시</th>
                <th>메세지</th>
                <th>기부 현황</th>
                <tr
                  v-for="(thsendBloodCard, idx) in thsendBloodCards"
                  :key="idx"
                >
                  <td>{{ thsendBloodCard.transactionCardDate }}</td>
                  <td>{{ thsendBloodCard.transactionCardMessage }}</td>
                  <td>기부 완료</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
      <div class="myBtn">
        <button @click="toInfoChange()">회원정보 수정</button>
        <button @click="doLogout()">로그아웃</button>
      </div>
      <div></div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { thSendBloodCard } from "@/api/bloodCard.js";

export default {
  data() {
    return {
      thsendBloodCards: [{}],
    };
  },
  methods: {
    ...mapActions(["logoutRemoveToekn"]),
    doLogout() {
      this.$router.push({ name: "login" });
      this.logoutRemoveToekn();
    },
    toInfoChange() {
      this.$router.push({ name: "infoChange", params: "" });
    },
    toWallet() {
      this.$router.push({ name: "daboWallet" });
    },
    toCharge() {
      this.$router.push({ name: "chargedabo", params: "" });
    },
  },
  mounted() {
    const vm = this;
    thSendBloodCard(
      function (response) {
        console.log(response.data);
        vm.thsendBloodCards = response.data;
      },
      function (err) {
        console.err(err);
      }
    );
  },
};
</script>

<style scoped>
.main-page {
  overflow: hidden;
}
.main-header {
  background-color: #e52d27;
  height: 24vh;
  justify-content: space-between;
  align-items: center;
}
.header-contents {
  justify-content: center;
  padding: 15px;
}
.greeting {
  font-weight: bold;
  font-size: 30px;
  text-align: center;
  color: white;
  margin-top: 20px;
}

/* profile */
.circle {
  border-radius: 50%;
  width: 100px;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 4em;
  color: white;
  background-color: #f06464;
  box-shadow: 2px 2px 5px rgb(193, 193, 193);
}
#wrapper {
  display: flex;
  justify-content: center;
  margin: 5px;
}
/* profile end */

.contents .div-wallet {
  font-weight: bold;
}
.contents button {
  border: none;
  background: #fff;
  font-weight: bold;
}
.contents-title {
  margin-top: 10%;
  margin-left: 10%;
  margin-right: 10%;
  align-items: center;
  justify-content: space-between;
}
.contents-subtitle p {
  font-size: small;
  font-weight: normal;
}
.contents-title div {
  align-items: center;
}
.contents-title div p {
  margin: 0;
}
.contents div {
  font-weight: bold;
}
.contents .div-wallet {
  font-weight: bold;
}
.contents-wallet {
  height: 20vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-wallet div {
  align-self: center;
  margin-left: 5%;
}
.detail-show {
  position: flex;
  float: right;
  margin-right: 30px;
  transform: translate(0px, -24px);
  font-size: small;
  font-weight: normal;
}
.detail-show button {
  border: none;
  background: #fff;
}
.contents-donation {
  height: 20vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-donation > div {
  align-self: center;
  text-align: left;
  margin-left: 5%;
}

/* table */
.donation-summary table th,
td {
  text-align: center;
  font-weight: lighter;
  font-size: 13px;
}
.donation-summary table {
  width: 290px;
}

.font-color-red {
  color: #e52d27;
}

/* button */
.myBtn button {
  display: block;
  margin: auto;
  background-color: white;
  color: black;
  border: none;
  border-radius: 5px 5px;
  width: 90%;
  margin-top: 10px;
  margin-bottom: 5px;
  box-shadow: 1px 1px;
}
</style>
