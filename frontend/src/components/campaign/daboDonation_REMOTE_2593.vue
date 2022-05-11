<template>
  <div>
    <div class="dabo_header">
      <h2 class="dabo_title">다보 후원</h2>
    </div>
    <div class="daboDonation-page">
      <h4 class="h-p">기부 금액(DABO)</h4>
      <p>{{ this.$store.state.wallet.cash }} DABO 보유</p>

      <!-- <table class="dabo_table">
                <tr>
                    <td @click="changeAmount(20000)">20000 DABO</td>
                    <td @click="changeAmount(40000)">40000 DABO</td>
                    <td @click="changeAmount(60000)">60000 DABO</td>
                </tr>
                <tr>
                    <td>80000 DABO</td>
                    <td>100000 DABO</td>
                    <td @click="textbox">직접 입력</td>
                </tr>
            </table> -->
      <div class="form_radio_group">
        <div class="form_radio_group-item">
          <input
            id="radio-1"
            type="radio"
            name="radio"
            value="10000"
            v-on:click="selectDabo = 10000"
          />
          <label for="radio-1">10000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input
            id="radio-2"
            type="radio"
            name="radio"
            value="20000"
            v-on:click="selectDabo = 20000"
          />
          <label for="radio-2">20000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input
            id="radio-3"
            type="radio"
            name="radio"
            value="30000"
            v-on:click="selectDabo =30000"
          />
          <label for="radio-3">30000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input
            id="radio-4"
            type="radio"
            name="radio"
            value="40000"
            

            v-on:click="selectDabo = 40000"
          />
          <label for="radio-4">40000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input
            id="radio-5"
            type="radio"
            name="radio"
            value="50000"
            v-on:click="selectDabo = 50000"
          />
          <label for="radio-5">50000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input
            id="radio-6"
            type="radio"
            name="radio"
            value="6"
            v-on:click="selectDabo = 60000"
            
          />
          <label for="radio-6">60000 DABO</label>
        </div>
      </div>
      <div id="dabo_input"></div>
      <div class="dabo-line"></div>

      <!-- <div>
                <h3>후원 기부 금액
                    <span class="h3_span">{{selectDabo}}DABO</span>
                </h3>
            </div> -->

      <div>
        <a href="#bDonation">
          <button class="btn_red_donation" @click="bloodDonation">
            <span>DABO 기부하기</span>
          </button>
        </a>
      </div>
      <div id="bDonation" class="modal-window">
        <div>
          <p>{{selectDabo}} DABO를 기부하시겠습니까?</p>
          <sub>
            <b-icon
              icon="exclamation-circle"
              style="width: 10px; height: 10px"
            ></b-icon>
            DABO 기부는 일정 시간의 대기 시간을 가진 뒤 자동으로 전달됩니다.
            전달 완료 후에는 취소하실 수 없으며, 관련 법령이 정하는 바에 따라
            기부가 취소될 수 있습니다.
          </sub>
          <div>
            <span>비밀키를 입력 하세요</span>
            <input type="text" v-model="privateKey" />
          </div>
          <div>
            <a href="#">
              <button class="btn_red_cancel">
                <span>취소하기</span>
              </button>
            </a>
            <button class="btn_red_modal" @click="cashTransfer()">
              <span>기부하기</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { leaveDeposit } from "@/utils/cashContract.js";
import * as walletService from "@/api/wallet.js";
import { createWeb3 } from "@/utils/web3.js";
export default {
  data() {
    return {

      selectDabo: 0,
      amount: "",
      privateKey: "",
      toAddress: this.$route.params.toAddress,
    };
  },
  methods: {
    textbox() {
      document.getElementById("dabo_input").innerHTML =
        '<input type="text" name="" id="" class="dir_text" > DABO <button class="btn_red_dabo" ><span>save</span></button>';
    },
    cashTransfer() {
      console.log("cashTransfer Start");
      console.log("this.$route.params.toAddress : ", this.toAddress);
      // console.log(Number(this.selectDabo) * 0.001);
      console.log(this.selectDabo)
      const vm = this;
      leaveDeposit(
        {
          escrowAddress: this.toAddress,
          amount: this.selectDabo,
        },
        vm.$store.state.user.walletAddress,
        this.privateKey,
        function () {
          alert("지불했습니다. 입금 확인 요청 바랍니다.");
          // this.fetchWalletInfo();
          vm.$router.push({ name: "daboConfirm", params: "" }); // UI 갱신
          //   vm.processing = false;
          //   vm.input.payAmount = null;
          //   vm.input.privateKey = "";
          //   vm.setActionText();
          //   vm.getWalletBalance(); // 지갑 정보 갱신
        },
        function (err) {
          console.error("입금 실패", err);
          alert("입금 실패했습니다.");
          vm.processing = false;
        }
      );
    },
    fetchWalletInfo() {
      const vm = this;
      walletService.findByUserId(this.userId, function (response) {
        const data = response.data;
        const web3 = createWeb3();
        data["balance"] = web3.utils.fromWei(
          data["balance"].toString(),
          "ether"
        );
        vm.$store.state.wallet = data;
      });
    },
  },
};
</script>

<style>
.daboDonation-page {
  margin: 0 auto;
  width: 90%;
}
.dabo_table {
  background-color: rgb(255, 246, 246);
  border-collapse: collapse;
  border: 1px solid rgb(255, 208, 208);
  width: 330px;
  height: 70px;
  text-align: center;
}
.dabo_table > tr {
  border: 2px solid rgb(255, 208, 208);
}
.dabo_table > tr > td {
  border: 2px solid rgb(255, 208, 208);
}
.dabo_input {
  text-align: center;
}
.dir_text {
  margin-left: 30px;
  margin-top: 10px;
}
.btn_red_dabo {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 50px;
  height: 30px;
  font-size: 12px;
  margin-top: 10px;
}
.h3_span {
  color: #e52d27;
  font-weight: 3px;
  margin-left: 70px;
}
.dabo-line {
  border-top: 2px solid rgb(211, 211, 211);
  margin-top: 15px;
  margin-bottom: 15px;
}

.form_radio_group {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-auto-rows: minmax(55px, auto);
  overflow: hidden;
  border-top: 1px solid #f08986;
  border-left: 1px solid #f08986;
}
.form_radio_group > div {
  border-right: 1px solid #f08986;
  border-bottom: 1px solid #f08986;
}
.form_radio_group-item {
  /* display: inline-block; */
  float: left;
  color: #f08986;
}
.form_radio_group input[type="radio"] {
  display: none;
}
.form_radio_group label {
  display: inline-block;
  cursor: pointer;
  padding: 0px 15px;
  line-height: 34px;

  border-right: none;
  user-select: none;
}

/* Checked */
.form_radio_group input[type="radio"]:checked + label {
  background: #f08986;
  color: white;
}

/* Hover */
.form_radio_group label:hover {
  color: #666;
}
</style>
