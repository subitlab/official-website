<script setup lang="ts">

import Content from "@/components/Content.vue";
import Link from "@/components/Link.vue";
import MemberList from "@/components/MemberList.vue";
import {useSiteContent} from "@/content/siteContent";

const siteContent = useSiteContent();
</script>

<template>
  <Content>
    <div class="header">
      <h1>加入 SubIT</h1>
      <p class="intro">
        {{ siteContent.join.intro }}
      </p>
    </div>

    <div class="info-grid">
      <div class="col">
        <h2>参考资源</h2>
        <div class="links">
          <Link :href="siteContent.join.recruitmentLink"
                title="语雀招新中转站"
                description="阅读招新要求 >"/>
          <Link href="https://subit.org.cn/"
                title="社团介绍"
                description="了解 SubIT >"/>
        </div>
      </div>

      <div class="col">
        <h2>技术，在育人</h2>
        <div class="stats">
          <div class="stat-item">
            <span class="label">985率近</span>
            <span class="value blue">60%</span>
          </div>
          <div class="stat-item">
            <span class="label">211率超过</span>
            <span class="value blue">90%</span>
          </div>
        </div>
      </div>

      <div class="col">
        <h2>毕业就业去向</h2>
        <div class="logos">
          <img src="/alumni_logos.png" alt="University and Company Logos" class="logos-img"/>
        </div>
      </div>
    </div>

    <MemberList :sections="siteContent.join.memberSections ?? []"/>
  </Content>
</template>

<style scoped lang="scss">
.header {
  margin-bottom: 28px;
  h1 {
    font-size: 32px;
    margin: 0 0 10px;
  }
  .intro {
    font-size: 16px;
    line-height: 1.5;
    max-width: 960px;
    color: #0d141c;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: 201px 196px minmax(300px, 1fr);
  justify-content: space-between;
  gap: 75px;
  margin-bottom: 18px;

  @media (max-width: 1024px) {
    grid-template-columns: 1fr;
    gap: 60px;
  }

  .col {
    h2 {
      font-size: 22px;
      margin-bottom: 14px;
      color: #000;
    }
  }
}

.stats {
  display: flex;
  flex-direction: column;
  gap: 12px;

  .stat-item {
    display: flex;
    align-items: baseline;
    gap: 10px;
    
    .label {
      font-size: 22px;
      color: #333;
    }
    
    .value {
      font-size: 32px;
      font-weight: 700;
      &.blue {
        color: #007AFF;
      }
    }
  }
}

.logos {
  .logos-img {
    width: 100%;
    height: auto;
    object-fit: contain;
  }
}

@media (max-width: 1024px) {
  .info-grid { grid-template-columns: 1fr; gap: 28px; }
  .logos-img { max-width: 460px; }
}
</style>
