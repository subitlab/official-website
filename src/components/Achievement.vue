<script setup lang="ts">
defineProps<{
  type: string,
  image?: string,
  imageAlt?: string,
  monogram?: string,
  href?: string,
  linkLabel?: string,
  title: string,
  description: string
}>();
</script>

<template>
  <article class="achievementContainer">
    <img v-if="image" class="achievementImage" :src="image" :alt="imageAlt ?? `${title}项目封面`"/>
    <div v-else class="achievementVisual" role="img" :aria-label="`${title}项目封面`">
      <span>{{ monogram ?? title.slice(0, 2) }}</span>
    </div>

    <div class="achievementInfo">
      <span :class="['type', type]">
        {{ type == 'event' ? '活动类 · Event' : type == 'archived' ? '已完成 · Completed' : '进行中 · Running' }}
      </span>
      <strong>{{ title }}</strong>
      <span class="comment">{{ description }}</span>
      <div class="spacer"></div>
      <a v-if="href" class="link" :href="href" target="_blank" rel="noopener noreferrer">
        {{ linkLabel ?? '了解项目详情 >' }}
      </a>
      <span v-else class="archive">暂无公开链接</span>
    </div>
  </article>
</template>

<style scoped lang="scss">
.achievementContainer {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 301px;
  height: 100%;
  min-height: 370px;
  overflow: hidden;
  border: 1px solid #e8edf5;
  border-radius: 12px;
  background-color: #F5F5F7;
  transition: border-color .2s, box-shadow .2s, transform .2s;

  &:hover {
    border-color: #b9d7f3;
    box-shadow: 0 12px 30px -18px rgba(13, 20, 28, .35);
    transform: translateY(-3px);
  }

  .achievementImage {
    width: 100%;
    height: 158px;
    min-height: 158px;
    object-fit: cover;
    object-position: center;
  }

  .achievementVisual {
    display: grid;
    width: 100%;
    height: 158px;
    min-height: 158px;
    place-items: center;
    overflow: hidden;
    background:
      radial-gradient(circle at 82% 18%, rgba(41, 144, 235, .42), transparent 32%),
      linear-gradient(145deg, #0d141c, #1b2d40);

    span {
      color: rgba(255, 255, 255, .92);
      font: 700 clamp(38px, 5vw, 52px)/1 "JetBrains Mono", monospace;
      letter-spacing: -.06em;
    }
  }

  .achievementInfo {
    display: flex;
    flex: 1;
    flex-direction: column;
    min-height: 0;
    padding: 14px 16px 16px;

    .type {
      align-self: flex-start;
      margin-bottom: 8px;
      padding: 3px 8px;
      border-radius: 999px;
      font-size: .78em;
      font-weight: 650;

      &.event { background: #e8f1fc; color: #0B5DC9; }
      &.archived { background: #f1ece9; color: #9b432f; }
      &.running { background: #e7f6ea; color: #247b35; }
    }

    strong {
      font-size: 17px;
      line-height: 1.4;
    }

    .comment {
      margin-top: 7px;
      color: #53616d;
      font-size: 14px;
      line-height: 1.65;
    }

    .spacer { flex: 1; min-height: 14px; }

    .link {
      color: #0066CC;
      font-size: .9em;
      font-weight: 600;
      text-decoration: none;

      &:hover { text-decoration: underline; text-underline-offset: 3px; }
    }

    .archive {
      color: #8a939c;
      font-size: .84em;
    }
  }
}
</style>
