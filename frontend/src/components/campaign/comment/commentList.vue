<template>
<div>
    <div class="container">
        <div class="campaign_item">
            <div class="row">
                    <div class="content">
                        <p class="c2">
                        {{ username }}
                        <span class="c3">({{ createdAt[0]+"-"+createdAt[1]+"-"+createdAt[2]+" "+createdAt[3]+":"+createdAt[4]+":"+createdAt[5]}})</span>
                        </p>
                    </div>
                    <!-- <div class="content" v-html="enterToBr(comment.comment)"></div> -->
                    <div class="content" >{{ content }}</div>
                    <div class="cbtn" v-if="reg">
                        <label @click="modifyComment">수정</label> |
                        <label @click="deleteComment">삭제</label>
                    </div>
            </div>
        </div>
    </div>
</div>    
</template>

<script>
import axios from "axios";
import {API_BASE_URL} from "@/config/index.js"

export default {
    data() {
        return {
          comment: [],
          reg: false
        }
    },
    props: {
      username: String,
      createdAt: String,
      content: String,
      commentId: Number
    },
    created() { 
        const user = this.$store.state.user.nickname;
        if(user === this.username) {
          this.reg = true;
        }
    },
    methods: {
      async modifyComment() {

        // 입력폼 생성
        // document.getElementById("modifyContent").innerHTML 
        //     = '<input type="text" name="content" id="" v-model="content" class="dir_text"> <button @click="modifyB" class="btn_red_dabo"><span>수정</span></button>'
      },
      async modifyB() {
        const commentData = {
          content: this.content,
          createdAt: this.createdAt,
          campaignId: this.$route.params.campaignId,
          commentId: this.commentId
        }

        console.log(commentData);

        await axios
            .put(API_BASE_URL+"/donationBoard/createBoard", this.commentId, commentData, {
              headers: {
                  "Content-Type": "multipart/form-data",
                  Authorization: `Bearer `+localStorage.getItem("accessToken"),
                },
            })
            .then((res) => {
              console.log(res);
              // this.$router.push("listBoard");
            });
      },
      async deleteComment() {
            await axios
            .delete(API_BASE_URL+`/donationBoard/detailBoard/${this.$route.params.campaignId}/comments/${this.commentId}`, this.commentId, {
              headers: {
                  "Content-Type": "multipart/form-data",
                  Authorization: `Bearer `+localStorage.getItem("accessToken"),
                },
            })
            .then((res) => {
              alert("댓글이 삭제되었습니다.");
              console.log(res);
              this.$router.go(this.$router.currentRoute);
            });
      }
    }
}
</script>

<style>
.comment {
  text-align: left;
  border-radius: 5px;
  background-color: #d6e7fa;
  padding: 10px 20px;
  margin: 10px;
  color: rgb(110, 110, 110);
}
.head {
  margin-bottom: 5px;
}
.content {
  padding: 5px;
  font-size: 13px;
}
.cbtn {
  text-align: right;
  color: #e52d27;
  margin: 5px 0px;
  font-size: 12px;
}
.c1 {
  font-size: 13px;
  color: rgb(136, 136, 136);
  float: right;
}
.c2 {
    margin-bottom: 0px;
}
.c3 {
  font-size: 12px;
  color: rgb(151, 151, 151);
}
.comment-img {
    width: 60px;
    height: 60px;
    border-radius: 20px;;
}
</style>