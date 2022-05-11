<template>
    <b-container v-if="houses && houses.length != 0" class="bv-example-row mt-3">
    <house-list-row
      v-for="(house, index) in houses"
      :key="index"
      :house="house"
      type="addonly"
    />
  </b-container>
  <b-container v-else class="bv-example-row mt-3">
    <b-row>
      <b-col><b-alert show>헌혈의집 목록이 없습니다.</b-alert></b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
    
}
</script>

<style lang="">
    
</style>