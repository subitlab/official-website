<script setup lang="ts">
defineProps<{
  href: string,
  title: string,
  description: string
}>();
</script>

<template>
  <a class="linkContainer" :href="href" target="_blank" rel="noreferrer">
    <div class="linkBox">
      <img src="@/assets/link.svg" alt="链接"/>
    </div>
    <div class="linkInfo">
      <strong>{{ title }}</strong>
      <span class="comment">{{ description }}</span>
    </div>
  </a>
</template>

<style scoped lang="scss">
.linkContainer {
  display: flex;
  gap: 1.5rem;

  height: 3rem;
  margin: 1rem 0;

  font-size: 1rem;

  cursor: pointer;
  color: #0d141c;
  text-decoration: none;

  .linkBox {
    display: flex;

    width: 1rem;
    height: 1rem;

    padding: 1rem;

    background-color: #E8EDF5;
    border-radius: .5rem;

    img {
      width: 1rem;
      height: 1rem;
    }
  }

  .linkInfo {
    display: flex;
    flex-direction: column;
  }
}
</style>
