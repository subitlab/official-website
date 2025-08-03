<script setup lang="ts">

import Content from "@/components/Content.vue";
</script>

<template>
  <Content class="center">
    <span class="title">Explore 5</span>
    <span class="subtitle">由 SubIT 与 NWWC Team 共同呈现。</span>
    <a class="link" href="https://bilibili.com/" target="_blank">观看演示片 ></a>
    <img class="image" src="@/assets/explore5.png" alt="Explore 5"/>
  </Content>
</template>

<style scoped lang="scss">
.center {
  text-align: center;
  background-color: black;
  color: white;
}
.title {
  margin-top: 56px;

  font-family: MiSans, monospace;
  font-weight: 700;
  font-size: 56px;
}
.subtitle {
  font-size: 28px;
}
.link {
  margin: 15px;
  font-size: 21px;
  color: #0066CC;
  text-decoration: none;
}
.image {
  width: 50%;
  margin-top: 60px;
  align-self: center;
}
</style>