<template>
  <div class="main-page">
    <div class="main-header">
      <div class="btn-alert">
        <button><img src="@/assets/bell.png" /></button>
      </div>
      <div class="d-flex header-contents">
        <div class="main-greeting">
          <p>{{ $store.state.user.nickname }} 님 <br />안녕하세요!</p>
        </div>
        <div class="btn-profile">
          <button><img src="@/assets/profile.png" /></button>
        </div>
      </div>
    </div>
    <div class="contents">
      <div class="div-bloodcard">
        <div class="contents-title d-flex">
          <div><p>내 헌혈증 기록</p></div>
          <div class="d-flex">
            <p>등록하기</p>
            <button @click="gobloodcardCreate" class="btn-plus">
              <img src="@/assets/plus.png" />
            </button>
          </div>
        </div>
        <div class="contents-bloodcard d-flex">
          <div class="imoge"><img src="@/assets/imoge.png" /></div>
          <div>
            <p>
              나의 헌혈증은 {{ bloodcardCnt }}개 입니다.
              <!-- <br />나의 기부 뱃지는
              2개 입니다. -->
            </p>
          </div>
        </div>
        <div class="detail-show">
          <button @click="goBloodcardList">자세히 보기</button>
        </div>
      </div>
      <div class="div-donation">
        <div class="contents-title">
          <div><p>마음을 기부해보세요</p></div>
        </div>
        <div class="contents-donation d-flex">
          <div class="thumnail-donation"><img src="@/assets/imoge.png" /></div>
          <div class="col-8">
            <div class="donation-title">
              <p>{{ title }}</p>
            </div>
            <div class="donation-summary">{{ content }}</div>
            <div class="progress-info d-flex">
              <p>헌혈증 {{ amount }}개 필요</p>
              <p>{{ deadLine }}까지</p>
            </div>
            <div class="progress donation-progress">
              <div
                class="progress-bar"
                role="progressbar"
                aria-valuenow="70"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 70%"
              >
                70%
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="div-ranking">
        <div class="contents-title">
          <div><p>DABO Ranking</p></div>
          <div class="contents-subtitle">
            <div><p>헌혈증 기부를 통해서 NFT 뱃지를 받아보세요!</p></div>
          </div>
        </div>
        <div class="contents-ranking">
          <div class="img-medal">
            <img src="@/assets/medal1.png" />
            <p>1위 000</p>
            <p></p>
          </div>
          <div class="img-medal d-flex">
            <div>
              <img src="@/assets/medal2.png" />
              <p>2위 000</p>
            </div>
            <div>
              <img src="@/assets/medal3.png" />
              <p>3위 000</p>
            </div>
          </div>
          <div class="under-rank">
            <div class="rank d-flex">
              <p>4위</p>
              <p>김OO</p>
            </div>
            <div class="rank d-flex">
              <p>5위</p>
              <p>김OO</p>
            </div>
            <div class="rank d-flex">
              <p>6위</p>
              <p>김OO</p>
            </div>
            <div class="rank d-flex">
              <p>7위</p>
              <p>김OO</p>
            </div>
            <div class="rank d-flex">
              <p>8위</p>
              <p>김OO</p>
            </div>
          </div>
        </div>
        <div class="detail-show">
          <button @click="goRanking()">자세히 보기</button>
        </div>
      </div>
      <div class="div-bloodreserve">
        <div class="contents-title">
          <div><p class="font-weight-bold">혈액 보유 현황</p></div>
        </div>
        <div class="contents-bloodreserve">
          <div class="col contents-title-bloodreserve d-flex">
            <p>오늘의 혈액보유량</p>
            <p class="font-color-red">전체 3.9일</p>
          </div>
          <div class="col d-flex">
            <div class="imoge">
              <img src="@/assets/imoge.png" />
              <p class="font-color-red">O</p>
              <p class="font-color-red">3.4</p>
            </div>
            <div class="imoge">
              <img src="@/assets/imoge.png" />
              <p class="font-color-red">A</p>
              <p class="font-color-red">3.4</p>
            </div>
            <div class="imoge">
              <img src="@/assets/imoge.png" />
              <p class="font-color-red">B</p>
              <p class="font-color-red">3.4</p>
            </div>
            <div class="imoge">
              <img src="@/assets/imoge.png" />
              <p class="font-color-red">AB</p>
              <p class="font-color-red">5.6</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findByBloodCard } from "@/api/bloodCard.js";
import { getDonationBoard } from "@/api/campaign.js";
export default {
  data() {
    return {
      bloodcardCnt: "",
      campaign: "",
      mediaUrl: "",
      title: "",
      content: "",
      amount: "",
      deadline: "",
      target: "",
    };
  },
  created() {
    this.getBloodcardCnt();
    this.getDonationContents();
  },
  methods: {
    getBloodcardCnt() {
      const vm = this;
      findByBloodCard(
        function (response) {
          vm.bloodcardCnt = response.data.length;
        },
        function (err) {
          console.log(err);
        }
      );
    },
    getDonationContents() {
      const vm = this;
      getDonationBoard(
        function (response) {
          vm.mediaUrl = response.data[0].mediaUrl;
          vm.title = response.data[0].title;
          vm.content = response.data[0].content;
          vm.amount = response.data[0].amount;
          vm.deadLine = response.data[0].deadLine;
          vm.target = response.data[0].target;
          console.log(response.data[0]);
        },
        function (err) {
          console.log(err);
        }
      );
    },
    goRanking() {
      this.$router.push({ name: "ranking" });
    },
    goBloodcardList() {
      this.$router.push({ name: "bloodcardList" });
    },
    gobloodcardCreate() {
      this.$router.push({ name: "bloodcardCreate" });
    },
  },
};
</script>

<style scoped>
.main-page {
}
.main-header {
  background-color: #e52d27;
  height: 24vh;
  justify-content: space-between;
  align-items: center;
}
.btn-alert {
  display: inline-block;
  width: 100%;
}
.btn-alert button {
  margin-right: 3%;
  margin-top: 3%;
  float: right;
  border: none;
  background-color: #e52d27;
}
.btn-alert button img {
  width: 30px;
}
.header-contents {
  justify-content: center;
}
.main-greeting {
  margin-right: 15%;
  font-weight: bold;
  font-size: x-large;
  text-align: center;
}
.btn-profile button {
  float: right;
  border: none;
  background-color: #e52d27;
}
.btn-profile button img {
  width: 80px;
}
.contents .div-bloodcard {
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
.contents .div-bloodcard {
  font-weight: bold;
}
.btn-plus {
  border: none;
  background-color: #fff;
}
.btn-plus img {
  width: 30px;
}
.contents-bloodcard {
  height: 14vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-bloodcard div {
  align-self: center;
  text-align: center;
  margin-left: 5%;
}
.imoge img {
  width: 80px;
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
.thumnail-donation img {
  width: 80px;
}
.contents-donation .donation-title {
  margin-right: 5%;
}
.contents-donation .donation-progress {
  margin-right: 5%;
}
.contents-donation .progress-info {
  justify-content: space-between;
  margin-right: 5%;
}
.contents-donation .progress-info p {
  margin: 0;
  font-size: 10px;
}
.contents-donation > div .donation-summary {
  margin-right: 5%;
  font-weight: normal;
}

.contents-ranking {
  height: 50vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-ranking div {
  align-self: center;
  text-align: center;
}
.contents-ranking p {
  margin: 0;
}
.contents-ranking .img-medal {
  justify-content: space-between;
}
.contents-ranking .img-medal > p {
  margin: 0;
}
.contents-ranking .img-medal > img {
  width: 25%;
}
.contents-ranking .img-medal > div img {
  width: 39%;
}
.under-rank {
  height: 21.5vh;
  margin-top: 4%;
  background-color: #f1f1f1;
  border-bottom-left-radius: 15px;
  border-bottom-right-radius: 15px;
}
.under-rank .rank {
  justify-content: space-between;
  margin-left: 16%;
  margin-right: 16%;
}
.div-ranking .detail-show button {
  background-color: #f1f1f1;
}
.contents-bloodreserve {
  height: 25vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-bloodreserve div {
  align-self: center;
  text-align: center;
}
.contents-title-bloodreserve {
  margin-left: 5%;
  margin-right: 5%;
  margin-bottom: 5%;
  justify-content: space-between;
}
.contents-bloodreserve {
  margin-bottom: 5%;
}
.contents-bloodreserve p {
  margin: 0;
}
.contents-title-bloodreserve > p {
  margin-top: 5%;
}
.font-color-red {
  color: #e52d27;
  font-size: 15px;
}
</style>
