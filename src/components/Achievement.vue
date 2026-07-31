<script setup lang="ts">
defineProps<{
  type: string,
  image: string,
  href: string,
  title: string,
  description: string
}>();
</script>

<template>
  <div class="achievementContainer">
    <img class="achievementImage" :src="image" alt="项目展示图片"/>
    <div class="achievementInfo">
      <span :class="['type', type]">{{
        type == 'event' ? '活动类成就' :
        type == 'archived' ? '已存档 Archived' :
        '运行中 Running'}}</span>
      <strong>{{ title }}</strong>
      <span class="comment">{{ description }}</span>
      <div class="spacer"></div>
      <a class="link" :href="href" target="_blank">访问 GitHub 仓库 ></a>
    </div>
  </div>
</template>

<style scoped lang="scss">
.achievementContainer {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 301px;
  min-height: 308px;

  border-radius: 12px;

  overflow: hidden;

  background-color: #F5F5F7;

  .achievementImage {
    width: 100%;
    height: 156px;
    min-height: 156px;

    object-fit: cover;
    object-position: center;
  }

  .achievementInfo {
    display: flex;
    flex-direction: column;
    min-height: 128px;

    padding: 10px;

    .type {
      font-size: 0.9em;
      font-weight: 500;
      &.event {
        color: #0B5DC9;
      }
      &.archived {
        color: #C92E0F;
      }
      &.running {
        color: #0ABB1F;
      }
    }

    .comment {
      word-break: break-all;
    }

    .spacer {
      flex: 1;
    }

    .link {
      font-size: 0.9em;
      color: #0066CC;
    }
  }
}
</style>
