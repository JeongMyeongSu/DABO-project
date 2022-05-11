<template>
  <div class="donationBoard-page">
    <div class="dabo_header">
      <h2 class="dabo_title">캠페인 목록</h2>
    </div>

    <!-- 검색창 -->
    <div class="submit-form">
      <div class="input-check d-flex">
        <input
          type="text"
          name="keyword"
          v-model="keyword"
          id="campaignSearch"
          placeholder="campaign search"
        />
        <button class="btn_red col-2" @click="goSearch()">
          <span>search</span>
        </button>
      </div>
    </div>

    <div class="contents-title d-flex">
      <div class="d-flex">
        <p>등록하기</p>
        <button @click="goCreate()" class="btn-plus">
          <img src="@/assets/plus.png" />
        </button>
      </div>
    </div>
    <div>
      <div class="container">
        <campaign-list-item
          v-for="campaign in campaignList"
          :key="campaign.id"
          v-bind="campaign"
        />
      </div>
    </div>
  </div>
</template>

<script>
import CampaignListItem from "@/components/campaign/donationBoardListItem";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: "donationBoardList",
  data: function () {
    return {
      campaignList: [],
      keyword: "",
    };
  },
  created() {
    const response = axios
      .get(API_BASE_URL + "/donationBoard/listBoard")
      .then((res) => {
        console.log(res.data);
        this.campaignList = res.data;
      });
    console.log(response);
  },
  components: {
    CampaignListItem,
  },
  methods: {
    goDetail() {
      this.$router.push({ name: "detailBoard", params: "" });
    },
    goCreate() {
      this.$router.push({ name: "createBoard", params: "" });
    },
    goSearch() {
      const response = axios
        .get(API_BASE_URL + "/donationBoard/search", {
          params: {
            keyword: this.keyword,
          },
        })
        .then((res) => {
          console.log(res.data);
          this.campaignList = res.data;
        });
      console.log(response);
    },
  },
};
</script>

<style>
.signup-page {
  text-align: -webkit-center;
  margin: 0 auto;
  width: 70%;
  font-weight: bold;
}
.dabo_header {
  background-color: #e52d27;
  text-align: center;
  height: 24vh;
}
.dabo_title {
  padding-top: 10vh;
  color: white;
  font-weight: bold;
}
#campaignSearch {
  margin-right: 2%;
}
.input-check {
  align-items: center;
  margin-left: 3%;
  margin-right: 3%;
}
.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  height: 35px;
  font-size: 12px;
}
.btn_red_c {
  background-color: #e52d27de;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 50%;
  height: 30px;
  font-size: 20px;
  margin-bottom: 7px;
}
.header {
  margin-top: 10%;
  margin-bottom: 10%;
  font-size: x-large;
}
.submit-form input {
  width: 100%;
  height: 35px;
  margin-top: 2%;
  margin-bottom: 2%;
  border-radius: 8px;
  background-color: #f6f6f6;
  border-color: #e8e8e8;
}
.submit-form input::placeholder {
  color: #bdbdbd;
  font-size: 12px;
  padding: 10px;
}

.input-bloodtype span {
  margin-top: 5%;
  font-size: 12px;
  float: left;
}
.campaign-img {
  width: 110px;
  height: 110px;
}
.ratio {
  color: #e52d27;
  font-size: 20px;
  font-weight: 3px;
  margin-right: 10px;
}
.span_day {
  float: right;
  position: absolute;
  margin-left: 100px;
}
.campaign_item {
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 5px;
}
.btn-plus {
  border: none;
  background-color: #fff;
}
.btn-plus img {
  width: 30px;
}
.contents-title {
  margin-left: 3%;
  margin-right: 3%;
  margin-bottom: 2%;
  align-items: center;
  justify-content: space-between;
  justify-content: right;
}
.contents-title div {
  align-items: center;
}
.contents-title div p {
  margin: 0;
}
.thumbnail-campaign {
  text-align: center;
}
</style>
