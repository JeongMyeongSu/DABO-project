<template>
  <div>
    <div class="wallet-header">
      <button @click="toBack()">back</button>
    </div>
    <div class="dabo_header">
      <h2 class="dabo_title">내 헌혈증 목록</h2>
    </div>
    <div class="myDonation-page">
      <p class="h-p">총 {{ bloodCards.length }}개 보유중</p>

      <div class="donationList">
        <p>기부할 헌혈증을 선택해주세요.</p>
        <div>
          <table class="dTable">
            <tr>
              <th></th>
              <th>증서 번호</th>
              <th>헌혈 일자</th>
              <th>혈핵원명</th>
            </tr>
            <tbody id="bloodCard">
              <tr v-for="(bloodCard, idx) in bloodCards" :key="idx">
                <td><input type="checkbox" v-model="bloodCardCheck[idx]" /></td>
                <td>{{ bloodCard.bloodCardNumber }}</td>
                <td>{{ bloodCard.donationDate }}</td>
                <td>{{ bloodCard.bloodHouse }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div>
        <a href="#bDonation">
          <button class="btn_red_donation" @click="bloodDonation">
            <span>헌혈증 기부하기</span>
          </button>
        </a>
      </div>
      <div id="bDonation" class="modal-window">
        <div>
          <p>총 n개의 헌혈증을 SSAFY 보건소로 전달합니다.</p>
          <p>총 {{ bloodCardCnt }}개의 헌혈증을 전달합니다.</p>
          <sub>
            <b-icon
              icon="exclamation-circle"
              style="width: 10px; height: 10px"
            ></b-icon>
            헌혈증은 일정 시간의 대기 시간을 가진 뒤 자동으로 전달됩니다. 전달
            완료 후에는 취소하실 수 없으며, 관련 법령이 정하는 바에 따라 기부가
            취소될 수 있습니다.
          </sub>
          <div>
            <span>비빌키를 입력 하세요</span>
            <input type="text" v-model="privateKey" />
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
import { findByBloodCard, bloodCardChageState } from "@/api/bloodCard.js";
import { bloodCardSend } from "@/utils/bloodCardDonation.js";

export default {
  data() {
    return {
      bloodCardCheck: [],
      bloodCards: [{}],
      privateKey: "",
      bloodCardCnt: 0,
    };
  },
  methods: {
    bloodDonation: function () {
      const vm = this;
      vm.bloodCardCnt = 0;
      for (var i = 0; i < this.bloodCardCheck.length; i++) {
        if (vm.bloodCardCheck[i]) {
          vm.bloodCardCnt++;
        }
      }
    },
    checkConfirm() {
      const vm = this;
      let bloodCardId = 0;
      for (var i = 0; i < this.bloodCardCheck.length; i++) {
        if (vm.bloodCardCheck[i]) {
          bloodCardId = vm.bloodCards[i].bloodCardId;

          bloodCardSend(
            this.$store.state.user.walletAddress,
            vm.privateKey,
            function () {
              bloodCardChageState(
                bloodCardId,
                "힘내세요",
                vm.$route.query.userId,
                function () {
                  vm.$router.push({ name: "donationConfirm", params: "" });
                },
                function (err) {
                  console.err(err);
                }
              );
            },
            function (err) {
              console.error(err);
            }
          );
        }
      }
    },
    BloodCardGet() {
      const vm = this;
      findByBloodCard(
        function (response) {
          vm.bloodCards = response.data;
        },
        function (err) {
          console.log(err);
        }
      );
    },
  },
  mounted() {
    this.BloodCardGet();
  },
};
</script>

<style lang="scss">
.myDonation-page {
  margin: 0 auto;
  width: 90%;
}
.donationList {
  border-radius: 5px;
  box-shadow: 2px 2px 2px 2px rgb(190, 190, 190);
}
.dTable {
  width: 100%;
  text-align: center;
}
.dTable th:first-child {
  width: 30px;
}
.dTable td:first-child {
  text-align: center;
}
.btn_red_donation {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 45%;
  height: 35px;
  font-size: 12px;
  float: right;
  margin-top: 15px;
}
.btn_red_modal {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 120px;
  height: 30px;
  font-size: 12px;
  float: right;
  margin-top: 15px;
}
.btn_red_cancel {
  background-color: #a3a3a3;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 120px;
  height: 30px;
  font-size: 12px;
  float: right;
  margin-top: 15px;
  margin-left: 5px;
}

.modal-window {
  position: fixed;
  background-color: rgba(15, 15, 15, 0.25);
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 999;
  visibility: hidden;
  opacity: 0;
  pointer-events: none;
  transition: all 0.3s;
  &:target {
    visibility: visible;
    opacity: 1;
    pointer-events: auto;
  }
  & > div {
    width: 350px;
    height: 170px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    background-size: cover;
    padding: 7px;
  }
  header {
    font-weight: bold;
  }
  h1 {
    font-size: 150%;
    margin: 0 0 15px;
  }
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

.modal-close {
  color: black;
  line-height: 50px;
  font-size: 100%;
  position: absolute;
  right: 10%;
  text-align: center;
  top: 10%;
  width: 70px;
  text-decoration: none;
  &:hover {
    color: black;
  }
}

/* Demo Styles */

a {
  color: inherit;
}

.modal-window {
  & > div {
    border-radius: 1rem;
  }
}

.modal-window div:not(:last-of-type) {
  margin-bottom: 15px;
}

small {
  color: lightgray;
}

.h-p {
  margin-top: 10px;
}
</style>
