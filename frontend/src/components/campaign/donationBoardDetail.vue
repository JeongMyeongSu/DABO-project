<template>
  <div>
    <div class="wallet-header">
      <button @click="toBack()">back</button>
    </div>
    <div class="dabo_header">
      <h2 class="dabo_title">캠페인 상세 보기</h2>
    </div>
    <div class="detailBoard-page">
      <div class="cbtn" v-if="reg">
        <label @click="modifyCampaign">수정</label> |
        <label @click="deleteCampaign">삭제</label>
      </div>
      <div class="div-campaignImg">
        <img
          :src="'http://localhost:8080' + mediaUrl"
          class="campaign-detail-img"
          alt=""
        />
        <h4>{{ title }}</h4>
        <p>{{ content }}</p>
        <div>
          <div class="small font-weight-bold">
            <span class="ratio">20%</span>
            <span class="float-right">15일 남음</span>
          </div>
          <div class="progress mb-2">
            <div
              class="progress-bar bg-danger"
              role="progressbar"
              style="width: 20%"
              aria-valuenow="20"
              aria-valuemin="0"
              aria-valuemax="100"
            ></div>
          </div>
        </div>
        <sub>n명의 후원자가 있습니다.</sub>
      </div>
      <div class="cpn-btn">
        <span>
          <button
            class="btn_red_campaign"
            style="margin-right: 10px; margin-left: 7px"
            @click="goDonationDetail()"
          >
            <span>헌혈증 기부하기</span>
          </button>
          <button class="btn_red_campaign" @click="goDaboDonationDetail()">
            <span>DABO로 후원하기</span>
          </button>
        </span>
      </div>
      <div class="line"></div>
      <comment-write @setCampaignId="setCampaignId" />
      <comment-list
        v-for="comment in comments"
        :key="comment.id"
        v-bind="comment"
        :comment="comment"
      />
    </div>
  </div>
</template>

<script>

import CommentWrite from "@/components/campaign/comment/commentWrite.vue";
import CommentList from "@/components/campaign/comment/commentList.vue";
import axios from "axios";
import { API_BASE_URL } from "@/config/index.js";

export default {
  name: "detailBoard",
  data() {
    return {
      campaign: [],
      content: "",
      title: "",
      username: "",
      walletAddressOfBoard: "",
      comments: [],
      campignId: 0,
      reg: false,
      userId: "",
      mediaUrl: "",
    };
  },
  async created() {
    // 캠페인 내용 get
    axios
      .get(
        API_BASE_URL +
          `/donationBoard/detailBoard/${this.$route.params.campaignId}`
      )
      .then((res) => {
        this.content = res.data.content;
        this.title = res.data.title;
        this.userId = res.data.userId;
        console.log(this.userId);
        this.walletAddressOfBoard = res.data.walletAddress;
        this.username = res.data.username;
        this.campaignId = res.data.campaignId;
        console.log(this.campaign);
        this.mediaUrl = res.data.mediaUrl;
      });

    // 댓글 내용 get
    await axios
      .get(
        API_BASE_URL +
          `/donationBoard/detailBoard/${this.$route.params.campaignId}/comments`
      )
      .then((res) => {
        this.comments = res.data;
      });

    // 유저 닉네임과 작성자 닉네임 비교
    const user = this.$store.state.user.nickname;
    if (user === this.username) {
      this.reg = true;
    }
  },
  components: {
    CommentWrite,
    CommentList,
  },
  methods: {
    goDonationDetail() {
      const vm = this;
      console.log(vm.userId);
      this.$router.push({ name: "myDonation", query: { userId: vm.userId } });
    },
    setCampaignId(campaignId) {
      this.campaignId = campaignId;
    },
    goDaboDonationDetail() {
      this.$router.push({
        name: "daboDonation",
        params: { toAddress: this.walletAddressOfBoard },
      });
    },
    toBack() {
      console.log(this.campignId);
      this.$router.go(-1);
    },
    modifyCampaign() {
      this.$router.push({
        name: "updateBoard",
        params: { campaignId: this.campaignId },
      });
    },
    async deleteCampaign() {
      await axios
        .delete(
          API_BASE_URL +
            `/donationBoard/detailBoard/${this.$route.params.campaignId}`,
          this.campaignId,
          {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ` + localStorage.getItem("accessToken"),
            },
          }
        )
        .then((res) => {
          alert("캠페인이 삭제되었습니다.");
          console.log(res);
          this.$router.push({ name: "listBoard" });
        });
    },
  },
};
</script>

<style>
.detailBoard-page {
  margin: 0 auto;
  width: 90%;
  font-weight: bold;
}
.btn_red_campaign {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 45%;
  height: 35px;
  font-size: 12px;
}
.header {
  margin-top: 10%;
  margin-bottom: 10%;
  font-size: x-large;
}
.ratio {
  color: #e52d27;
  font-size: 20px;
  font-weight: 3px;
  margin-right: 10px;
}
.div-campaignImg {
  text-align: center;
}
.campaign-detail-img {
  width: 100%;
  margin-bottom: 5%;
  margin-top: 5%;
}
.line {
  border-top: 2px solid rgb(211, 211, 211);
  margin-top: 10px;
  margin-bottom: 10px;
}
.cpn-btn {
  margin-top: 13px;
  margin-bottom: 13px;
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
  font-family: "NicoMoji" !important;
  color: white;
  vertical-align: -webkit-baseline-middle;
}
</style>
