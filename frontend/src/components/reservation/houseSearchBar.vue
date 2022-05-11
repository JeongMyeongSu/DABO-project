<template>
    <div>
        <b-row>
    <b-col cols="12" align="left">
      <b-row class="mt-4 mb-4 text-center">
        <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
        <b-col class="sm-4">
          <b-form-select
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList"
          ></b-form-select>
        </b-col>

        <b-col class="sm-3">
          <b-form-select
            v-model="gugunCode"
            :options="guguns"
            @change="searchApt"
          ></b-form-select>
        </b-col>
      </b-row>
    </b-col>
  </b-row>
    </div>
</template>

<script>
export default {
    
}
</script>

<style lang="">
    
</style>