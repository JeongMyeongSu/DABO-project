<template>
  <div>
    <div id="main">
      <nav
        v-bind:class="active"
        v-on:click.prevent
        style="box-shadow: 1px 1px 1px 2px grey"
      >
        <a
          href="/home"
          class="home"
          v-on:click="[makeActive('home'), toHome()]"
        >
          <div class="homeSet" style="text-align: center">
            <i class="fa-solid fa-house fa-3x"></i>
          </div>
          Home
        </a>
        <a
          href="#"
          class="donation"
          v-on:click="[makeActive('donation'), toDonation()]"
        >
          <div class="donationSet" style="text-align: center">
            <i class="fa-solid fa-comment-medical fa-3x"></i>
          </div>
          Donate
        </a>
        <a
          href="#"
          class="reservation"
          v-on:click="[makeActive('reservation'), toReservation()]"
        >
          <div class="reservationSet" style="text-align: center">
            <i class="fa-solid fa-clock fa-3x"></i>
          </div>
          Res
        </a>
        <a
          href="#"
          class="myDABO"
          v-on:click="[makeActive('myDABO'), toMyDabo()]"
        >
          <div class="myDABOSet" style="text-align: center">
            <i class="fa-solid fa-user fa-3x"></i>
          </div>
          myDABO
        </a>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: "home",
    };
  },
  methods: {
    makeActive: function (item) {
      this.active = item;
    },
    toHome() {
      this.$router.push({ name: "home" });
    },
    toDonation() {
      this.$router.push({ name: "listBoard" });
    },
    toReservation() {
      this.$router.push({ name: "searchMap" });
    },
    toMyDabo() {
      this.$router.push({ name: "myDabo" });
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  text-align: center;
}

a:hover {
  text-decoration: none;
}

nav {
  display: block;
  /* width: 360px; */
  max-width: 360px;
  min-width: 360px;
}

nav {
  display: inline-block;
  margin: 50px auto 45px;
  background-color: white;
  box-shadow: 0 1px 1px #ccc;
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  border-color: gray;
}

nav a {
  display: inline-block;
  padding: 18px 30px;
  color: gray !important;
  font-weight: bold;
  font-size: 0.3rem;
  text-decoration: none !important;
  line-height: 1;
  background-color: transparent;
  vertical-align: middle;
}

nav.home .home .homeSet,
nav.donation .donation .donationSet,
nav.reservation .reservation .reservationSet,
nav.myDABO .myDABO .myDABOSet {
  color: red;
}
</style>
