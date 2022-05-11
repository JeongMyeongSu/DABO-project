<template>
    <div class="commentInput-page">
        <!-- <div v-if="this.modifyComment != null" class="regist_form"> -->
        <div>
            <form class="form" @submit.prevent="submitForm">
            <textarea name="content" id="content" v-model="content" class="text_comment" cols="30" rows="3"></textarea>
            
                <button
                class="btn_red_comment"
                 type="submit"
                >
                등록
                </button>
            
            </form>
        </div>

        <!-- <div v-else class="regist_form">
            <textarea
                id="comment"
                name="comment"
                v-model="comment"
                cols="35"
                rows="2"
            ></textarea>
            <button
                class="g-actions-button23 g-actions-button23-default"
                @click="registComment"
            >
                등록
            </button>
        </div> -->
    </div>
</template>

<script>
import axios from "axios";
import {API_BASE_URL} from "@/config/index.js"

export default {
    data() {
        return {
            comment: {
                commentId: 0,
                content: "",
                username: "",
                crearedAt: "",
                campaignId: 0
            }
        }
    },
    props: {
        campaignId: Number
    },
    methods: {
       async submitForm() {
            const commentData = {
                content: this.content,
                createdAt: this.crearedAt,
                campaignId: this.$route.params.campaignId
            };

            console.log(commentData);

            await axios
                .post(API_BASE_URL + `/donationBoard/detailBoard/${this.$route.params.campaignId}/comments`, commentData, {
                headers: {
                    Authorization: `Bearer `+ localStorage.getItem("accessToken"),
                    }
                })
                .then((res) => {
                console.log(res);
                // this.$router.push(`${this.$route.params.campaignId}`);
                this.$router.go(this.$router.currentRoute);
        });
       },
    }
}
</script>

<style>
.commentInput-page {
  margin: 0 auto;
  height: 90px;
  font-weight: bold;
}
.btn_red_comment {
    background-color: #e52d27;
    color: #fff;
    border-radius: 10px;
    border: none;
    width: 20%;
    height: 25px;
    font-size: 10px;
    float:right;
}
</style>