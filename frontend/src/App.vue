<template>
  <div class="view">
    <router-view />
    <footer>
      <div class="DABO-footer">
        <footerBar />
      </div>
    </footer>
  </div>
</template>

<script>
import footerBar from "@/components/footer/daboFooter";
export default {
  components: {
    footerBar,
  },
};
</script>
<style>
* {
  font-family: "Arita", "Lato" !important;
  overflow: -moz-hidden-unscrollable;
}
::-webkit-scrollbar {
  display: none;
}
@font-face {
  font-family: "Arita";
  src: url("assets/fonts/Arita4.0_M.ttf") format("truetype");
  font-weight: 400;
}
@font-face {
  font-family: "Arita";
  src: url("assets/fonts/Arita4.0_L.ttf") format("truetype");
  font-weight: 300;
}
@font-face {
  font-family: "Arita";
  src: url("assets/fonts/Arita4.0_B.ttf") format("truetype");
  font-weight: 700;
}
@font-face {
  font-family: "Lato";
  src: url("assets/fonts/Lato-Light.ttf") format("truetype");
  font-weight: 400;
}
@font-face {
  font-family: "Lato";
  src: url("assets/fonts/Lato-Regular.ttf") format("truetype");
  font-weight: 300;
}
@font-face {
  font-family: "Lato";
  src: url("assets/fonts/Lato-Bold.ttf") format("truetype");
  font-weight: 700;
}
@font-face {
  font-family: "NicoMoji";
  src: url("assets/fonts/NicoMoji-Regular.ttf") format("truetype");
  font-weight: 400;
}
.view {
  background-color: #fff;
  border: 5px #000;
  width: 360px;
  margin: 0 auto;
}

/* footer */
footer {
  position: fixed;
  bottom: 0;
}
footer .DABO-footer {
  position: relative;
  height: 100px;
  z-index: 2;
}
body {
  padding-bottom: 100px;
  /* 생략 */
}
</style>
