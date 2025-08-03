<script setup lang="ts">

import Content from "@/components/Content.vue";
</script>

<template>
  <Content class="center">
    <span class="title">SSubITO</span>
    <span class="subtitle">一个应用，登录所有 SubIT 社团开发的平台。</span>
    <a class="link" href="https://ssubito.subit.org.cn/" target="_blank">通过 SSO 登录 ></a>
    <img class="image" src="@/assets/ssubito.png" alt="SSubITO"/>
  </Content>
</template>

<style scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@700&display=swap');

.center {
  text-align: center;
}
.title {
  margin-top: 56px;

  font-family: 'JetBrains Mono', monospace;
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
  width: 70%;
  margin-top: 60px;
  align-self: center;
}
</style>