<script setup lang="ts">
</script>

<template>
  <div class="center">
    <span class="title">西楼大屏管理系统</span>
    <span class="subtitle">这块屏幕为你所有。</span>
    <a class="link" href="https://screen.subit.org.cn/" target="_blank">实时状态 ></a>
    <img class="image" src="@/assets/screen.png" alt="West Building Screen Management System"/>
  </div>
</template>

<style scoped lang="scss">
.center {
  display: flex;
  flex-direction: column;
  text-align: center;
  background-color: white;
  color: black;
}
.title {
  margin-top: 56px;

  font-weight: 700;
  font-size: 56px;
  color: #1a1a1a;
}
.subtitle {
  font-size: 32px;
  margin-top: 10px;
  color: #1a1a1a;
}
.link {
  margin: 15px;
  font-size: 21px;
  color: #0066CC;
  text-decoration: none;
}
.image {
  width: 80%;
  max-width: 1200px;
  margin-top: 20px;
  align-self: center;
  box-shadow: 0 4px 20px rgba(0,0,0,0.1);
  border-radius: 8px;
}
</style>
