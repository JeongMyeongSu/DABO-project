<template>
  <div class="signup-page">
    <p class="header">회원정보 수정</p>
    <p class="user-name"> 김싸피</p>
    <div class="submit-form">
      <div class="input-check">
        <input type="text" name="nickname" placeholder="NickName" />
        <button class="btn_input btn_red col-2">
          <span>check</span>
        </button>
      </div>
      <div class="input-bloodtype">
        <span>Blood Type</span>
        <div class="form_radio_btn d-flex">
          <input
            type="radio"
            id="contactChoice1"
            name="contact"
            value="A"
            checked
          />
          <label for="contactChoice1">A</label>
          <input type="radio" id="contactChoice2" name="contact" value="B" />
          <label for="contactChoice2">B</label>
          <input type="radio" id="contactChoice3" name="contact" value="AB" />
          <label for="contactChoice3">AB</label>
          <input type="radio" id="contactChoice4" name="contact" value="O" />
          <label for="contactChoice4">O</label>
        </div>
      </div>

      <div class="input-check d-flex">
        <input class="" type="email" name="email" placeholder="Email" />
        <button class="btn_input btn_red col-2">
          <span>check</span>
        </button>
      </div>
      <input type="password" name="password" placeholder="Password" />
      <input
        type="password"
        name="passwordChk"
        placeholder="Password Check"
      />
      <button class="btn_request">
        수정 완료
      </button>
      <button class="btn_cancel" @click="toMyDabo()">
        돌아가기
      </button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    toMyDabo() {
      this.$router.push({name: 'myDabo'})
    }
  }
};
</script>

<style>
.signup-page {
  margin: 0 auto;
  width: 70%;
  font-weight: bold;
}
.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 100%;
  font-size: 12px;
}
.btn_request {
  background-color: green;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 80%;
  font-size: 20px;
  margin-top: 5%;
}
.btn_cancel {
  background-color: red;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 80%;
  font-size: 20px;
  margin-top: 5%;
}
.header {
  margin-top: 10%;
  margin-bottom: 10%;
  font-size: 15px;
  text-align: left;
}
.user-name {
  text-align: left;
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
.input-check {
  position: relative;
}
.btn_input {
  position: absolute;
  right: 1%;
  top: 5px;
  height: 29px;
}

.input-bloodtype span {
  margin-top: 5%;
  font-size: 12px;
  float: left;
}

.form_radio_btn {
  width: 100%;
  height: 40px;
  border: 1px solid #eae7e7;
  border-radius: 8px;
  margin-bottom: 10px;
}
.form_radio_btn input[type="radio"] {
  display: none;
}
.form_radio_btn label {
  width: 25%;
  display: block;
  border-radius: 8px;
  margin: 0 auto;
  text-align: center;
  height: -webkit-fill-available;
  line-height: 45px;
}

.form_radio_btn input[type="radio"]:checked + label {
  background: #e52d27a1;
  color: #fff;
}

.form_radio_btn label:hover {
  color: #666;
}

.form_radio_btn input[type="radio"] + label {
  background: #f9fafc;
  color: #666;
}
</style>
