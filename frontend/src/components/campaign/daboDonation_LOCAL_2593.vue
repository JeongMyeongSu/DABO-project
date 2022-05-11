<template>
    <div>
        <div class="wallet-header">
      <button @click="toBack()">back</button>
    </div>
        <div class="dabo_header">
            <h2 class="dabo_title">다보 후원</h2>
        </div>
        <div class="daboDonation-page">
            <h4 class="h-p">기부 금액(DABO)</h4>
            <p>{{this.$store.state.wallet.cash}} DABO 보유</p>

            <table class="dabo_table">
                <tr>
                    <td>20000 DABO</td>
                    <td>40000 DABO</td>
                    <td>60000 DABO</td>
                </tr>
                <tr>
                    <td>80000 DABO</td>
                    <td>100000 DABO</td>
                    <td @click="textbox">직접 입력</td>
                </tr>
            </table>
            <div id="dabo_input"></div>
            <div class="dabo-line"></div>
            <div>
                <h3>후원 기부 금액
                    <span class="h3_span">200 DABO</span>
                </h3>
            </div>

            <div>
            <a href="#bDonation">
            <button class="btn_red_donation" @click="bloodDonation">
                <span>DABO 기부하기</span>
            </button>
            </a>
            </div>
            <div id="bDonation" class="modal-window">
                <div>
                        <p>200 DABO를 기부하시겠습니까?</p>
                        <sub>
                            <b-icon icon="exclamation-circle" style="width: 10px; height: 10px;"></b-icon>
                            DABO 기부는 일정 시간의 대기 시간을 가진 뒤 자동으로 전달됩니다.
                            전달 완료 후에는 취소하실 수 없으며, 관련 법령이 정하는 바에 따라 기부가 취소될 수 있습니다.
                        </sub>
                        <div>
                        <span>비빌키를 입력 하세요</span>
                        <input type="text" v-model="privateKey">
                      </div>
                        <div>
                        <a href="#">
                            <button class="btn_red_cancel">
                                <span>취소하기</span>
                            </button>
                        </a>
                        <button class="btn_red_modal" @click="checkConfirm()">
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

export default {
    data() {
        return {
            privateKey : "",
            toAddress : this.$route.params.toAddress,
        }
    },
    methods: {
        textbox() {
            document.getElementById("dabo_input").innerHTML 
            = '<input type="text" name="" id="" class="dir_text"> DABO <button class="btn_red_dabo"><span>save</span></button>'
        },
        checkTest(){
            console.log("toAddress : " ,this.toAddress)
        },
        cashTransfer(){
            const vm = this;
            leaveDeposit(
                {
                escrowAddress: "0x16B8D5aC26341506d7b03E0B52709B135Bf873dF",
                amount: 100
                },
                vm.$store.state.user.walletAddress,
                this.privateKey,
                function() {
                alert("지불했습니다. 입금 확인 요청 바랍니다.");
                // UI 갱신
                //   vm.processing = false;
                //   vm.input.payAmount = null;
                //   vm.input.privateKey = "";
                //   vm.setActionText();
                //   vm.getWalletBalance(); // 지갑 정보 갱신
                },
                function(err) {
                console.error("입금 실패", err);
                alert("입금 실패했습니다.");
                vm.processing = false;
                }
            );
        },
        toBack() {
        this.$router.go(-1);
        }
        
    }
}
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
</style>