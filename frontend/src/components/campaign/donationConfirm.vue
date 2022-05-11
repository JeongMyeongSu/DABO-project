<template>
    <div>
        <div class="check_icon">
            <b-icon icon="check2-circle" scale="2" variant="success" style="width: 120px; height:120px;"></b-icon>
        </div>
        
        <div class="check_text">
           <p>헌혈증 전달이 완료되었습니다.</p>
            <button class="btn_red_check" @click="goHome()">
                <span>Home 으로</span>
            </button>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        goHome() {
            this.$router.push({name: 'home'})
        }
    }
}
</script>

<style>
.check_icon {
    height: 170px;
    padding-left: 120px;
    padding-top: 200px;
}
.check_text {
    text-align: center;
    padding-top: 180px;
}
.btn_red_check {
    background-color: #e52d27;
    color: #fff;
    border-radius: 10px;
    border: none;
    width: 120px;
    height: 35px;
    font-size: 12px;
    margin-top: 15px;
}
</style>