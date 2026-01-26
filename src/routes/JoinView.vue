<script setup lang="ts">

import Content from "@/components/Content.vue";
import Link from "@/components/Link.vue";
import Member from "@/components/Member.vue";

const members = [
  {
    image: '/cps.jpg',
    name: '社长 | CPS1',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: '副社长 | CPS2',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS3',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS4',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS5',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS6',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS7',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS8',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS9',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS10',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS11',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS12',
    description: 'CPSCPSCPSCPS'
  },
];
</script>

<template>
  <Content>
    <div class="header">
      <h1>加入 SubIT</h1>
      <p class="intro">
        我们欢迎每一位对我们感兴趣的北大附中学生的加入！如果你希望成为我们的一员，SubIT 每年会在春季和秋季各进行一次招新，春季招新通常在每学年下半学期的 3 月至 4 月之间，秋季招新则在上半学期的 8 月至 10 月期间进行。
      </p>
    </div>

    <div class="info-grid">
      <div class="col">
        <h2>参考资源</h2>
        <div class="links">
          <Link href="https://www.yuque.com/"
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

    <h2 class="section-title">27 届社员</h2>

    <div class="membersContainer">
      <Member v-for="(member, index) in members"
              :key="index"
              :image="member.image"
              :name="member.name"
              :description="member.description"/>
    </div>
  </Content>
</template>

<style scoped lang="scss">
.header {
  margin-bottom: 60px;
  h1 {
    font-size: 48px;
    margin-bottom: 30px;
  }
  .intro {
    font-size: 18px;
    line-height: 1.6;
    max-width: 800px;
    color: #333;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 40px;
  margin-bottom: 80px;

  @media (max-width: 1024px) {
    grid-template-columns: 1fr;
    gap: 60px;
  }

  .col {
    h2 {
      font-size: 24px;
      margin-bottom: 30px;
      color: #000;
    }
  }
}

.stats {
  display: flex;
  flex-direction: column;
  gap: 30px;

  .stat-item {
    display: flex;
    align-items: baseline;
    gap: 10px;
    
    .label {
      font-size: 24px;
      color: #333;
    }
    
    .value {
      font-size: 40px;
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

.section-title {
  font-size: 32px;
  margin-bottom: 40px;
}

.membersContainer {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 40px 20px;
  justify-items: start;
}
</style>
