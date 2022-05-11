<template>
  <div class="wallet-page">
    <div class="wallet-header">
      <button @click="toBack()">back</button>
    </div>

    <div class="user-header">
      <span> {{ $store.state.user.nickname }} 님의 </span> 
      <span id="dabo-logo"> DABO </span>
      <span> Wallet </span>
    </div>

    <div id="logo">
      <div class="logoCircle">D</div>
    </div>
    <div class="wallet-control">
      <i class="fa-solid fa-circle-plus"></i>
      <span @click="goCharge()"> 충전하기 </span>
      <i class="fa-solid fa-circle-minus"></i>
      <span> 출금하기 </span>
    </div>
    <div class="contents-wallet d-flex" style="height: 400px;">
      <div>
        <p style="font-weight: bold;">DABO Token 보유 현황</p>
        <span style="font-size: 40px;">{{this.wallet.cash}} DABO</span>
        <p id="dabo-krw"><i class="fa-solid fa-won-sign"></i> {{this.wallet.cash}} KRW</p>
        <span style="font-weight: bold">내 지갑 주소</span>
        <span style="margin-left: 150px;" @click="copy">
          <i class="fa-solid fa-copy"></i>복사하기
        </span><br>
        <span>{{ $store.state.user.walletAddress }}</span>
        <p style="font-weight: bold; font-size: 20px; margin-top: 5%">DABO Token 활동</p>
        <!-- v-for 변환 예정 -->
        <div class="wallet-act">
          <!-- <span><i class="fa-solid fa-comment-dollar fa-2xl"></i></span> -->
          <div class="act-type">
            <span>충전</span>
            <span style="margin-left: 60%">200DABO</span>
          </div>
          <div class="act-detail">
            <span>'22.03.14</span>
            <span style="margin-left: 55%; font-size: 10px;"><i class="fa-solid fa-won-sign"></i> 20,000 KRW</span>
          </div>
          <div class="act-type">
            <span>기부</span>
            <span style="margin-left: 60%">100DABO</span>
          </div>
          <div class="act-detail">
            <span>'22.03.14</span>
            <span style="margin-left: 55%; font-size: 10px;"><i class="fa-solid fa-won-sign"></i> 10,000 KRW</span>
          </div>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import * as walletService from "@/api/wallet.js";
import { createWeb3 } from "@/utils/web3.js";

export default {
  data() {
    return {
      wallet: {
        id: 0,
        ownerId: null,
        address: "",
        balance: 0,
        payBalance : 0,
        cash: 0,
        receivingCount: 0,
      },
      userId: this.$store.state.user.id,
      walletAddress: this.$store.state.user.walletAddress,
    };
  },
  methods: {
    toBack() {
      this.$router.go(-1)
    },
    goCharge() {
      this.$router.push({path: 'dabowallet/chargeDabo'})
    },
    fetchWalletInfo() {
      /**
       * 지갑 조회
       */
      console.log("지갑을 조회합니다")
      const vm = this;
      walletService.findByUserId(this.userId, function(response) {
        const data = response.data;
        console.log(data)
        const web3 = createWeb3();
        data["balance"] = web3.utils.fromWei(
          data["balance"].toString(),
          "ether"
        );
        vm.wallet = data;
        vm.$store.commit("setWallet", data)

      });
    },
    
  },
  mounted() {
    this.fetchWalletInfo();
  },
}
</script>

<style scoped>
.wallet-page {
  overflow: -moz-hidden-unscrollable;
}
.wallet-header {
  background-color: #e52d27;
  height: 2.5em;
  justify-content: space-between;
  align-items: center;
}
.wallet-header button {
  border: 0;
  outline: 0;
  background-color: #e52d27;
  font-family: 'NicoMoji' !important;
  color: white;
  vertical-align: -webkit-baseline-middle;
}
.logoCircle {
  border-radius: 50%;
    width: 70px;
    height: 70px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 4em;
    color: white;
    background-color: #e52d27;
    box-shadow: 2px 2px 5px rgb(193, 193, 193);
    font-family: 'NicoMoji' !important;
}
#logo {
    display: flex;
    justify-content: center;
    margin: 20px;
}

.user-header {
  text-align: center;
  font-weight: bold;
  font-size: 20px;
  margin-top: 2rem;
}
#dabo-logo {
  font-family: 'NicoMoji' !important;
}

.wallet-control {
  text-align: right;
  margin-right: 5%;
}

.contents-wallet {
  height: 300px;
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

#dabo-krw {
  color: gray;
  font-size: 15px;
}

.wallet-act{
  margin: 5%;
}

.act-detail {
  font-size: 10px;
}

</style>