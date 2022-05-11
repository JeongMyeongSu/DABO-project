<template>
  <div class="cardlist-page">
    <div class="cardlist-header">
      <button @click="backBtn"><img src="@/assets/back.png" /></button>
    </div>
    <div class="cardlist-title">
      <span>나의 헌혈증</span>
    </div>
    <div
      v-for="(bloodCard, bloodCardId) in bloodLists"
      :key="bloodCardId"
      :per-page="perPage"
      :current-page="currentPage"
      class="d-flex cardlist-conentents"
    >
      <div class="imoge col-4"><img src="@/assets/border-imoge.png" /></div>
      <div class="col-8">
        <span>{{ bloodCard.donationDate }} 헌혈증</span>
        <p>증서번호 : {{ bloodCard.bloodCardNumber }}</p>
        <p>혈액원명 : {{ bloodCard.bloodHouse }}</p>
        <p>성명 : {{ bloodCard.userName }}</p>
        <p>헌혈종류 : {{ bloodCard.donationType }}</p>
      </div>
    </div>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="bloodcard"
      align="center"
    >
    </b-pagination>
  </div>
</template>

<script>
import { findByBloodCard } from "@/api/bloodCard.js";
export default {
  data() {
    return {
      // 몇개 씩 보여줄지
      perPage: 3,
      // 현재 페이지
      currentPage: 1,
      bloodCards: [],
    };
  },
  mounted() {
    this.getList();
  },
  computed: {
    bloodLists() {
      const items = this.bloodCards;
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
    rows() {
      return this.bloodCards.length;
    },
  },
  methods: {
    getList() {
      const vm = this;
      findByBloodCard(
        function (response) {
          vm.bloodCards = response.data;
          console.log(vm.bloodCards);
        },
        function (err) {
          console.log(err);
        }
      );
    },
    backBtn() {
      this.$router.push({ name: "home" });
    },
  },
};
</script>

<style scoped>
.cardlist-page {
  overflow: hidden;
}
.cardlist-header {
  height: 5%;
  background: #e52d27;
}
.cardlist-header button {
  margin-left: 5%;
  border: none;
  background: #e52d27;
}
.cardlist-header button img {
  width: 35%;
}
.cardlist-title {
  text-align: center;
  margin-top: 5%;
  margin-bottom: 5%;
  font-weight: 700;
}
.cardlist-conentents {
  align-items: center;
  margin-bottom: 5%;
}
.cardlist-conentents .imoge {
  text-align: center;
}
.cardlist-conentents .imoge img {
  width: 85%;
}
.cardlist-conentents div {
  margin-left: 3%;
}
.cardlist-conentents div p {
  margin: 0;
}
</style>
