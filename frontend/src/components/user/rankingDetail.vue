<template>
  <div class="ranking-page">
    <div class="contents-title d-flex">
      <div><p>My Ranking</p></div>
      <div class="d-flex">
        <button class="btn-question">
          <img src="@/assets/question.png" />
        </button>
      </div>
    </div>
    <div class="contents-myrank">
      <div class="imoge"><img src="@/assets/medal3.png" /></div>
      <div>
        <p>27 badge<br />52 Point</p>
        <button class="share">SHARE</button>
      </div>
    </div>
    <div class="contents-rank">
      <div class="d-flex">
        <div class="col-4 img-badge">
          <img src="@/assets/badge-rank1.png" />
        </div>
        <div class="col-4 rank-userinfo">
          <p>김싸피</p>
          <p>39 badge</p>
          <p>95 Point</p>
        </div>
        <div class="col-4 img-medal">
          <img src="@/assets/medal1.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.btn-question {
  border: none;
  background: #fff;
}
.contents-title {
  font-weight: bold;
  justify-content: space-between;
  margin-left: 6%;
  margin-right: 6%;
}
.contents-myrank {
  text-align: center;
  height: 24vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  box-shadow: 0 1px 1px rgb(0 0 0 / 25%), 0 2px 2px rgb(0 0 0 / 20%),
    0 4px 4px rgb(0 0 0 / 15%), 0 8px 8px rgb(0 0 0 / 10%);
}
.contents-myrank .imoge img {
  width: 20%;
}
.contents-rank {
  text-align: center;
  height: 15vh;
  margin-top: 3%;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 15px;
  border: 1px solid #e8e8e8;
  background: #f6f6f6;
}
.contents-rank div {
  align-self: center;
}
.contents-rank .img-badge img {
  width: 100%;
}
.contents-rank .rank-userinfo p {
  margin-bottom: 0;
}
.img-medal img {
  width: 80%;
}
.share {
  font-weight: bold;
  border-radius: 20px;
  padding: 1%;
  padding-left: 3%;
  padding-right: 3%;
}
</style>
