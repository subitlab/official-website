<script setup lang="ts">
import type {ProjectItem} from "@/content/siteContent";

defineProps<{ item: ProjectItem }>();
</script>

<template>
  <article class="project-showcase" :class="{ 'has-secondary': item.secondaryImage }">
    <div class="copy">
      <h3>{{ item.title }}</h3>
      <p>{{ item.subtitle }}</p>
      <a :href="item.href" target="_blank" rel="noreferrer">{{ item.linkLabel }}</a>
    </div>
    <div class="visual">
      <img class="primary" :src="item.image" :alt="item.imageAlt"/>
      <img v-if="item.secondaryImage" class="secondary" :src="item.secondaryImage" :alt="`${item.imageAlt}移动端界面`"/>
    </div>
  </article>
</template>

<style scoped lang="scss">
.project-showcase {
  display: flex;
  flex-direction: column;
  min-height: 662px;
  overflow: hidden;
  background: white;
  color: #1d1d1f;
  text-align: center;
}

.copy {
  position: relative;
  z-index: 2;
  padding-top: 50px;

  h3 {
    margin: 0;
    font-family: "JetBrains Mono", monospace;
    font-size: clamp(38px, 3.76vw, 56px);
    line-height: 1.1;
    letter-spacing: -.01em;
  }

  p {
    margin: 5px 16px 0;
    font-size: clamp(20px, 1.88vw, 28px);
    line-height: 1.3;
  }

  a {
    display: inline-block;
    margin-top: 10px;
    color: #06c;
    font-size: clamp(17px, 1.42vw, 21px);
    line-height: 29px;
    text-decoration: none;

    &:hover { text-decoration: underline; }
  }
}

.visual {
  position: relative;
  flex: 1;
  min-height: 390px;
  margin-top: 22px;

  .primary {
    display: block;
    width: min(80%, 960px);
    height: 100%;
    max-height: 470px;
    margin: 0 auto;
    object-fit: contain;
    object-position: top center;
  }

  .secondary {
    position: absolute;
    right: 20%;
    bottom: 2%;
    width: min(17%, 180px);
    max-height: 78%;
    object-fit: contain;
  }
}

@media (max-width: 700px) {
  .project-showcase { min-height: 540px; }
  .copy { padding-top: 42px; }
  .visual { min-height: 310px; margin-top: 12px; }
  .visual .primary { width: 94%; max-height: 360px; }
  .visual .secondary { right: 8%; width: 22%; }
}

@media (max-height: 540px) and (min-width: 701px) {
  .project-showcase { min-height: 440px; }
  .copy { padding-top: 24px; }
  .visual { min-height: 250px; margin-top: 8px; }
  .visual .primary { max-height: 280px; }
}
</style>
