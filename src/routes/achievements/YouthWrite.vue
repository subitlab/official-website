<script setup lang="ts">

import Content from "@/components/Content.vue";
</script>

<template>
  <Content class="center">
    <span class="title">创意写作工坊</span>
    <span class="subtitle">我们把对写作的爱藏在袖子里</span>
    <a class="link" href="https://youthwrite.subit.org.cn/" target="_blank">开始书写 ></a>
    <img class="image" src="@/assets/youthwrite1.png" alt="创意写作工坊"/>
    <img class="imagePhone" src="@/assets/youthwrite2.png" alt="创意写作工坊"/>
  </Content>
</template>

<style scoped lang="scss">
.center {
  text-align: center;
}
.title {
  margin-top: 56px;

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
.imagePhone {
  position: relative;
  width: 17%;
  margin-top: -29.6%;
  margin-left: 70%;
}
</style>