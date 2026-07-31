<script setup lang="ts">

import Content from "@/components/Content.vue";
import Achievement from "@/components/Achievement.vue";
import LinkButton from "@/components/LinkButton.vue";
import {computed} from "vue";
import {useRoute} from "vue-router";
import {useSiteContent} from "@/content/siteContent";

const route = useRoute();
const isAll = computed(() => route.path.endsWith('/all'));
const siteContent = useSiteContent();
</script>

<template>
  <Content>
    <h1>{{ isAll ? '所有项目与成就' : '项目与成就' }}</h1>
    <div class="achievementsContainer">
      <Achievement v-for="(achievement, index) in siteContent.achievements"
                   :key="index"
                   :image="achievement.image"
                   :title="achievement.title"
                   :description="achievement.description"
                   :href="achievement.href"
                   :type="achievement.type"/>
    </div>
    <RouterLink v-if="!isAll" class="checkAll" to="/achievements/all">
      <LinkButton type="normal">查看所有成就 >></LinkButton>
    </RouterLink>
    <RouterLink v-else class="checkAll" to="/achievements">
      <LinkButton type="normal">返回项目概览</LinkButton>
    </RouterLink>
  </Content>
</template>

<style scoped lang="scss">
.achievementsContainer {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(260px, 1fr));
  gap: 24px 12px;
  justify-items: center;
}

h1 { margin: 16px 0 28px; font-size: 32px; }

.checkAll {
  margin-top: 30px;
  margin-left: auto;
  margin-bottom: 30px;
}
</style>
