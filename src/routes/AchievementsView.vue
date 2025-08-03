<script setup lang="ts">

import Content from "@/components/Content.vue";
import Achievement from "@/components/Achievement.vue";
import LinkButton from "@/components/LinkButton.vue";

const achievements = [
  {
    type: "event",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
  {
    type: "archived",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
  {
    type: "running",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
  {
    type: "event",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
  {
    type: "archived",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
  {
    type: "running",
    title: "2020毕业典礼·邀请函生成器",
    image: "/invitation.png",
    href: "https://github.com/",
    description: "这是一段描述这是一段描述这是一段描述这是一段描述这是一段描述"
  },
];
</script>

<template>
  <Content>
    <h1>项目与成就</h1>
    <div class="achievementsContainer">
      <Achievement v-for="achievement in achievements"
                   :image="achievement.image"
                   :title="achievement.title"
                   :description="achievement.description"
                   :href="achievement.href"
                   :type="achievement.type"/>
    </div>
    <RouterLink class="checkAll" to="/achievement/all">
      <LinkButton type="normal">查看所有成就 >></LinkButton>
    </RouterLink>
  </Content>
</template>

<style scoped lang="scss">
.achievementsContainer {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
  gap: 30px;
  justify-items: center;
}

.checkAll {
  margin-top: 30px;
  margin-left: auto;
  margin-bottom: 30px;
}
</style>