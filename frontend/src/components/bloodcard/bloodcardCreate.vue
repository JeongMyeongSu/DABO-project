<template>
  <div class="createCard-page">
    <p class="header">Create BloodCard</p>
    <div class="submit-form">
      <input type="text" name="name" placeholder="Name" v-model="name" />
      <div class="input-gender">
        <span>Gender</span>
        <div class="form_radio_btn d-flex">
          <input
            type="radio"
            v-model="gender"
            id="contactChoice1"
            name="contact"
            value="M"
          />
          <label for="contactChoice1">M</label>

          <input
            type="radio"
            v-model="gender"
            id="contactChoice2"
            name="contact"
            value="F"
          />
          <label for="contactChoice2">F</label>
        </div>
      </div>
      <input
        v-model="birthDate"
        name="birthdate"
        placeholder="Birth Date    ex> 1996-02-23"
      />
      <input
        type="text"
        v-model="cardNumber"
        name="cardnumber"
        placeholder="BloodCard Number"
      />
      <select class="input-select" v-model="typeSelected">
        <option disabled value="">Please select Blood Donation Type</option>
        <option>전혈 400ml</option>
        <option>전혈 400ml</option>
        <option>전혈 400ml</option>
      </select>
      <input
        type="text"
        v-model="donationDate"
        name="donationDate"
        placeholder="Blood Donation Date"
      />
      <input
        type="text"
        v-model="bloodHouse"
        name="bloodHouse"
        placeholder="bloodHouse"
      />
      <input
        type="text"
        v-model="privateKey"
        name="privateKey"
        placeholder="privateKey"
      />

      <button @click="submit" class="btn_red">
        <span>CONFIRM</span>
      </button>
    </div>
  </div>
</template>

<script>
import { createBloodCard } from "@/api/bloodCard.js";
import { bloodCardRegister } from "@/utils/bloodCardDonation.js";

export default {
  data() {
    return {
      name: "",
      gender: "",
      birthDate: "",
      cardNumber: "",
      donationDate: "",
      typeSelected: "",
      bloodHouse: "",
      privateKey: "",
    };
  },

  methods: {
    submit() {
      const vm = this;
      const cardData = {
        userName: this.name,
        userGender: this.gender,
        userBirth: this.birthDate,
        bloodCardNumber: this.cardNumber,
        donationDate: this.donationDate,
        donationType: this.typeSelected,
        bloodHouse: this.bloodHouse,
      };
      createBloodCard(
        cardData,
        function(response){
          console.log(response.data)
          bloodCardRegister(
            response.data.bloodCardId,
            vm.privateKey,
            function(){
              vm.$router.push({name: 'bloodcardList'})
            },
            function(err){
              console.log(err);
            }
          )
        },
        function(err){
          console.log(err);
        }
      )
    },
  },
};
</script>

<style scoped>
.createCard-page {
  text-align: -webkit-center;
  margin: 0 auto;
  width: 70%;
  font-weight: bold;
}
.btn_check {
  width: 20% !important;
}
.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 35%;
  font-size: 12px;
}
.header {
  margin-top: 10%;
  margin-bottom: 10%;
  font-size: x-large;
}
.input-select {
  height: 35px;
  margin-top: 2%;
  margin-bottom: 2%;
  border-radius: 8px;
  background-color: #f6f6f6;
  border-color: #e8e8e8;
}
.input-select {
  width: 100%;
  color: #bdbdbd;
  font-size: 12px;
  padding: 10px;
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
  top: 8px;
  height: 29px;
}

.input-gender span {
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
  width: 100%;
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
