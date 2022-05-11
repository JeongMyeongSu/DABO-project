<template>
  <div>
    <div class="dabo_header">
      <h2 class="dabo_title">캠페인 등록</h2>
    </div>
    <div class="createBoard-page">
      <form class="form" @submit.prevent="submitForm">
      <div class="submit-form">
        <p class="p_title">이미지 등록하기</p>
        <input multiple ref="image" name="mediaUrl" type="file" id="files" @change="handleFileChange($event)">
        <p class="p_title">제목</p>
        <input type="text" name="title" v-model="campaign.title" id="" placeholder="캠페인 제목">
        <p class="p_title">내용</p>
        <input type="text" name="content" v-model="campaign.content" id="" placeholder="캠페인 설명">
        <p class="p_title">목표 갯수</p>
        <input type="text" name="amount" v-model="campaign.amount" id="" placeholder="목표 갯수" style="width: 80px; margin-right: 5px;">개
        <p class="p_title">목표 DABO</p>
        <input type="text" name="target" v-model="campaign.target" id="" placeholder="목표 DABO" style="width: 80px; margin-right: 5px;">DABO
        <p class="p_title">마감 기한</p>
        <input type="date" name="deadline" v-model="campaign.deadline" id="input_date">
        <button type="submit" class="btn_red" v-if="this.type === 'modify'">
          <span>캠페인 수정</span>
        </button>
        <button type="submit" class="btn_red" v-else>
          <span>캠페인 등록</span>
        </button>
      </div>
      </form>
    </div>
  </div>

</template>

<script>
import axios from "axios";
import {API_BASE_URL} from "@/config/index.js"
import * as walletService from "@/api/wallet.js";
import { createWeb3 } from "@/utils/web3.js";

export default {
  data() {
    return {
      campaign: {
        campaignId: 0,
        title: "",
        content: "",
        amount: 0,
        target: "",
        deadline: new Date(),
        mediaUrl: null,
        walletAddress: ""
      },
      userId: this.$store.state.user.id,
      userWalletAddress: this.$store.state.user.walletAddress,
    }
  },
  props: {
    type: {type: String}
  },
  created() {
    if(this.type === "modify") {
      axios
        .get(API_BASE_URL+`/donationBoard/detailBoard/${this.$route.params.campaignId}`)
        .then((res) => {
          this.campaign = res.data;
        });
    }
  },
  methods: {
    fetchWalletInfo() {
      /**
       * 지갑 조회
       */
      console.log("userWalletAddress",this.userWalletAddress);
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
    handleFileChange(event) {
      const file = event.target.files[0];
      this.campaign.mediaUrl = file;
    },
    async submitForm() {
      if(this.campaign.mediaUrl === null){
        alert("이미지를 등록해주세요")
      }
      const formData = new FormData();
      const deadline = document.querySelector("#input_date").value;
      formData.append("title", this.campaign.title);
      formData.append("content", this.campaign.content);
      formData.append("media", this.campaign.mediaUrl);
      formData.append("amount", this.campaign.amount);
      formData.append("target", this.campaign.target);
      formData.append("deadLine", deadline);
      formData.append("walletAddress", this.userWalletAddress);

      if(this.type === "modify" ? this.modifyCamapign() : this.registCampaign(formData));
      
    },
    async registCampaign(formData) {
      const response = await axios
        .post(API_BASE_URL+"/donationBoard/createBoard", formData, {
           headers: {
               "Content-Type": "multipart/form-data",
               Authorization: `Bearer `+localStorage.getItem("accessToken"),
             },
         })
        .then((res) => {
          console.log(res);
          this.$router.push("listBoard");
        });
      console.log(response);
    },
    async modifyCamapign() {
      const response = await axios
        .put(API_BASE_URL+`/donationBoard/detailBoard/${this.$route.params.campaignId}`, this.campaign, {
           headers: {
               Authorization: `Bearer `+localStorage.getItem("accessToken"),
             },
         })
        .then((res) => {
          console.log(res);
          this.$router.push({
            name: 'detailBoard',
            params: {campaignId: this.campaign.campaignId}
          });
        });
      console.log(response);
    }
  },
  mounted() {
    
    this.fetchWalletInfo();
  },
};
</script>

<style>
.createBoard-page {
  margin: 0 auto;
  width: 90%;
  font-weight: bold;
}
.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 100%;
  height: 35px;
  font-size: 12px;
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
.p_title {
  margin-top: 10px;
  margin-bottom: 0px;
}
</style>
