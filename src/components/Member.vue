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
    width: 130px;
    height: 130px;
    border-radius: 5px;
    object-fit: cover;

  }

  .memberInfo {
    display: flex;
    flex-direction: column;

    margin: 10px 0;
    font-size: 14px;

    .comment {
      max-width: 130px;
      word-break: break-all;
    }
  }
}
</style>
