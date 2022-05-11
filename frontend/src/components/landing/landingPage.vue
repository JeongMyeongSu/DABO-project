<template>
  <div class="landing">
    <div class="titleText">
      <span
        v-for="(t, index) in title"
        :key="index"
        class="item"
        :style="{animationDelay: index*200+'ms'}"
        v-text="t"
        id="title"
        />
      <br>
      <span
        v-for="(t, index) in subtitle"
        :key="index"
        class="item"
        :style="{animationDelay: index*70+'ms'}"
        v-text="t"
        id="subtitle"
        />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      title: 'DABO',
      subtitle: 'Donation + ABO',
    }
  },
  created(){
    setTimeout( () => this.$router.push({ path: '/user/login'}), 4000)
  }
};
</script>

<style lang="stylus" scoped>
@keyframes text-in {
  0% {
    transform: translate(0, -20px);
    opacity: 0;
  }
}

.item {
  display: inline-block;
  min-width: 0.3em;
  color: white;
  animation: text-in .8s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;

}
.landing {
  position: relative;
  background-color: rgb(229, 45, 39);
  height: 100vh;
  text-align: center;
  z-index: 4;
}

.titleText {
  position:absolute;
  top:50%; left:50%;
  transform: translate(-50%, -50%);
}

.titleText span {
  font-family: "NicoMoji" !important;
}

#title {
  font-size: 3rem;
  font-family: "Lato"; 

}

#subtitle {
  font-size: 1rem;
}

</style>
