<template>
  <div class="charge-page">
    <div class="charge-header">
      <div class="d-flex header-contents">
        <div class="greeting">
          <p style="font-family: 'NicoMoji' !important;">DABO 충전하기</p>
        </div>
      </div>
    </div>

    <div class="contents">
      <!-- header -->
      <p>충전하실 DABO 개수를 선택해주세요</p>

      <!-- wallet -->
      <div class="contents-wallet d-flex">
        <div>
          <span style="font-weight: bold;">DABO Token 보유 현황</span><br>
          <span style="font-weight: lighter;">{{ $store.state.wallet.cash }} DABO</span><br>
          <span style="font-weight: bold;">내 지갑 주소</span><br>
          <span style="font-weight: lighter;">{{ $store.state.user.walletAddress }}</span>
        </div>
      </div>

      <br/>
      <!-- select dabo -->
      <div class="form_radio_group">
        <div class="form_radio_group-item">
          <input id="radio-1" type="radio" name="radio" value="1" v-model="selectDabo" checked>
          <label for="radio-1">10000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input id="radio-2" type="radio" name="radio" value="2" v-model="selectDabo">
          <label for="radio-2">20000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input id="radio-3" type="radio" name="radio" value="3" v-model="selectDabo">
          <label for="radio-3">30000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input id="radio-4" type="radio" name="radio" value="4" v-model="selectDabo">
          <label for="radio-4">40000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input id="radio-5" type="radio" name="radio" value="5" v-model="selectDabo">
          <label for="radio-5">50000 DABO</label>
        </div>
        <div class="form_radio_group-item">
          <input id="radio-6" type="radio" name="radio" value="6" v-model="selectDabo">
          <label for="radio-6">60000 DABO</label>
        </div>
      </div>

      <button class="btn_red">
        <span @click="toPayDabo()">DABO 충전하기</span>
      </button>
    </div>

  </div>
</template>

<script>

export default {
  data() {
    return {
      selectDabo: 0,
    }
  },
  methods: {
    toPayDabo() {
      if (this.selectDabo === 0) {
        alert('충전하실 금액을 선택해주세요')
      } else{
        this.$router.push({name: 'paydabo', params: {selected: this.selectDabo}})
      }
    },
  },
}
</script>

<style scoped>
.charge-page {
  overflow: hidden;
}
.charge-header {
  background-color: #e52d27;
  height: 24vh;
  justify-content: space-between;
  align-items: center;
}
.header-contents {
  justify-content: center;
  padding: 15px;
}
.greeting {
  font-weight: bold;
  font-size: 30px;
  text-align: center;
  color: white;
}
.greeting p {
  margin-left: 0px;
  margin-top: 30px;
}

.contents p {
  text-align: center;
  font-size: 20px;
  margin-top: 20px;
}

.contents .div-wallet {
  font-weight: bold;
}
.contents-wallet {
  height: 20vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-wallet div {
  align-self: center;
  margin-left: 5%;
}

.form_radio_group {
	display: inline-block;
	overflow: hidden;
}
.form_radio_group-item {
	display: inline-block;
	float: left;    
  color: #f08986;
}
.form_radio_group input[type=radio] {
	display: none;
}
.form_radio_group label {
	display: inline-block;
	cursor: pointer;
	padding: 0px 15px;
	line-height: 34px;
	border: 1px solid #f08986;
	border-right: none;
	user-select: none;
}

.form_radio_group .form_radio_group-item:first-child label {
	/* border-radius: 6px 0 0 6px; */
}
.form_radio_group .form_radio_group-item:last-child label {
	/* border-radius: 0 6px 6px 0; */
	/* border-right: 1px solid #999; */
}

/* Checked */
.form_radio_group input[type=radio]:checked + label {
	background: #f08986;
  color: white;
}

/* Hover */
.form_radio_group label:hover {
	color: #666;
}


.btn_red {
  background-color: #e52d27;
  color: #fff;
  border-radius: 10px;
  border: none;
  width: 30%;
  height: 35px;
  font-size: 12px;
  float: right;
  margin-top: 50px;
}
</style>