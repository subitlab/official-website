<script setup lang="ts">
defineProps<{
  image: string,
  name: string,
  description: string
}>();
</script>

<template>
  <div class="memberContainer">
    <img :src="image" alt="成员照片"/>
    <div class="memberInfo">
      <strong>{{ name }}</strong>
      <span class="comment">{{ description }}</span>
    </div>
  </div>
</template>

<style scoped lang="scss">
.memberContainer {
  display: flex;
  flex-direction: column;

  img {
    width: 120px;
    height: 120px;
    border-radius: 5px;
  }

  .memberInfo {
    display: flex;
    flex-direction: column;

    margin: 10px 0;

    .comment {
      max-width: 120px;
      word-break: break-all;
    }
  }
}
</style>