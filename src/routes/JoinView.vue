<script setup lang="ts">

import Content from "@/components/Content.vue";
import Link from "@/components/Link.vue";
import Member from "@/components/Member.vue";

const members = [
  {
    image: '/cps.jpg',
    name: 'CPS1',
    description: 'CPSCPSCPSCPS'
  },
  {
    image: '/cps.jpg',
    name: 'CPS2',
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
    <h1>加入 SubIT</h1>
    <p>
      我们欢迎每一位对我们感兴趣的北大附中学生的加入！如果你希望成为我们的一员，SubIT 每年会在春季和秋季各进行一次招新，春季招新通常在每学年下半学期的 3 月至 4 月之间，秋季招新则在上半学期的 8 月至 10 月期间进行。
    </p>

    <h2>参考资源</h2>
    <!-- TODO 填写链接 -->
    <Link href="https://www.yuque.com/"
          title="语雀招新中转站"
          description="阅读详细的招新要求"/>
    <Link href="https://subit.org.cn/"
          title="社团介绍"
          description="了解 SubIT"/>

    <h2>Class of 2026 Members</h2>

    <div class="membersContainer">
      <Member v-for="member in members"
              :image="member.image"
              :name="member.name"
              :description="member.description"/>
    </div>
  </Content>
</template>

<style scoped lang="scss">
.membersContainer {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 20px;
  justify-items: center;
}
</style>