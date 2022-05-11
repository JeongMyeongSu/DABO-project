<template>
  <div class="findpassword-page">
    <p class="header">Find Password</p>
    <form>
      <div class="submit-form">
        <input type="text" name="name" placeholder="Name" />
        <input type="email" name="email" placeholder="Email" />
        <button class="btn_findpassword">
          <span>Find Password</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.findpassword-page {
  text-align: -webkit-center;
  margin: 0 auto;
  width: 70%;
  font-weight: bold;
}
.btn_findpassword {
  background-color: #e52d27;
  color: #fff;
  border-radius: 30px;
  border: none;
  margin-top: 30%;
  width: 100%;
  height: 35px;
  font-size: 12px;
  font-weight: bold;
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
  border: 1px solid #e8e8e8;
}
.submit-form input::placeholder {
  color: #bdbdbd;
  font-size: 12px;
  padding: 10px;
}
</style>
