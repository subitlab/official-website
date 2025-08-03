<script setup lang="ts">

import Content from "@/components/Content.vue";
import LinkButton from "@/components/LinkButton.vue";
</script>

<template>
  <Content>
    <h1>技术支持</h1>
    <h2>本地支持</h2>
    <p>在工作日的午餐休息时间和活动时段内，可提供离线技术支持服务。请提前通过发送电子邮件的方式预订您的服务时段。</p>
    <h2>在线支持</h2>
    <p>如需在线技术支持，请直接向 SubIT 发送电子邮件。在邮件中请清晰地描述您的问题。主要的沟通方式将是通过电子邮件进行。</p>
    <LinkButton type="primary" href="mailto:contact@subit.org.cn">Email SubIT</LinkButton>
  </Content>
</template>

<style scoped lang="scss">

</style>