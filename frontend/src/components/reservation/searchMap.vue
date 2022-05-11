<template>
  <div>
    <div class="dabo_header">
      <h2 class="dabo_title">헌혈의 집 예약</h2>
    </div>
    <div class="bloodHouse-page">
      <h4 class="h-p">헌혈의 집 선택</h4>
      <p>우리집 주변의 헌혈의 집을 찾아보세요.</p>
      <div class="submit-form">
        <div class="input-check d-flex">
        <!-- <input type="text" name="bloodhouse" id="bloodhouse" placeholder="blood house search">
        <button class="btn_red col-2">
            <span>search</span>
          </button> -->
          <div class="content-header">
            <b-row>
              <b-col>
                <div style="align: center">
                  <span
                    class="task__tag task__tag--design"
                    style="margin-right: 150px"
                    >시/도</span
                  >
                  <span class="task__tag task__tag--design">구/군</span>
                </div>

                <house-search-bar></house-search-bar>
              </b-col>
            </b-row>
          </div>
          </div>
      </div>
      <div>
        <house-list />
      </div>
      
    <!-- <section class="test"> -->
      <div id="map"></div>
    <!-- </section> -->
    </div>
  </div>
</template>


<script>
import HouseSearchBar from "@/components/reservation/houseSearchBar.vue";
import HouseList from "@/components/reservation/houseList.vue";

export default {
  data() {
    return {
      map: null,
      markers: [],
      latitude: 0,
      longitude: 0
    }
  },
  created() {
    if (!("geolocation" in navigator)) {
      return;
    }

    // get position
    navigator.geolocation.getCurrentPosition(pos => {
      this.latitude = pos.coords.latitude;
      this.longitude = pos.coords.longitude;

      if (window.kakao && window.kakao.maps) {

        this.initMap();

      } else {
        const script = document.createElement("script");
        /* global kakao */
        script.onload = () => kakao.maps.load(this.initMap);
        script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=408674fc88231a4d61801f16ed1fba1b";
        document.head.appendChild(script);
      }

    }, err => {
      alert(err.message);
    })
  },
  components: {
      HouseSearchBar,
      HouseList
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);
      this.displayMarker([[this.latitude, this.longitude]]);
    },
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
          (position) => new kakao.maps.LatLng(...position)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
            (position) =>
                new kakao.maps.Marker({
                  map: this.map,
                  position,
                })
        );

        const bounds = positions.reduce(
            (bounds, latlng) => bounds.extend(latlng),
            new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    }
  }
}
</script>


<style scoped>
.bloodHouse-page {
  margin: 0 auto;
  width: 90%;
}
/* .test {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
} */

#map {
  width: 320px;
  height: 400px;
  border: 1px #a8a8a8 solid;
}


</style>