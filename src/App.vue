<script setup lang="ts">
</script>
<template>
  <nav>
    <RouterLink to="/"><!--TODO SubIT logo-->SubIT</RouterLink>
    <RouterLink to="/about">关于我们</RouterLink>
    <RouterLink to="/join">加入我们</RouterLink>
    <RouterLink to="/achievements">项目成就</RouterLink>
    <RouterLink to="/support">提供支持</RouterLink>
  </nav>
  <main>
    <RouterView />
  </main>
</template>