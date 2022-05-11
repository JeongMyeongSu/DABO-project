<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <!-- <button class="modal-default-button" @click="$emit('close')">
            <i style="color: #505352" class="far fa-times-circle fa-2x"></i>
          </button> -->
          <div class="modal-header">
            <slot name="header"> default header </slot>
          </div>

          <div class="modal-body">
            
              <div class = "warning" >
              <p style="font-size: 16px">경고!</p>
              <p>1. 지갑 비밀키를 잃어버리지 마세요! 한 번 잃어버리면 복구 할 수 없습니다</p>
              <p>2. 공유하지 마세요! 비밀키가 악위적인 사이트에 노출되면 당신의 자산이 유실될 수 있습니다.</p>
              <p>3. 백업을 만들어 두세요! 종이에 적어서 오프라인으로 관리하세요!</p>
            </div>

            <slot name="body"> </slot>
          </div>

          <div class="modal-footer">
            <slot name="footer">
              
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;

}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
.modal-container {
  width: 340px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  /* font-family: "Dancing Script", cursive; */
}
.modal-footer {
  font-family: sans-serif;
}
.modal-header {
  align-items: center;
  justify-content: center !important;
}
.modal-header h3 {
  margin-top: 0;
  color: #505352;
  text-align: center;
  font-weight: bold;
}
.modal-body {
  margin: 10px 0;
  /* text-align: center; */
}
.modal-default-button {
  float: right;
  background-color: transparent;
  border: 0;
}
.modal-enter {
  opacity: 0;
}
.modal-leave-active {
  opacity: 0;
}
.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
.modal-body,
.modal {
  color: #666 !important;
  /* font-family: "Nanumsquare", Tahoma, Geneva, Verdana, sans-serif; */
  font-size: 12px;
  
}

.warning {
  background-color: #e52d27;
  color : white;
  font-size: 12px;
  padding: 1rem;
}
</style>