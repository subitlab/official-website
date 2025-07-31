<script setup lang="ts">
const { href } = defineProps<{
  href: string,
  title: string,
  description: string
}>();

function openInNewTab() {
  window.open(href, '_blank');
}
</script>

<template>
  <div class="linkContainer" @click="openInNewTab">
    <div class="linkBox">
      <img src="@/assets/link.svg" alt="链接"/>
    </div>
    <div class="linkInfo">
      <strong>{{ title }}</strong>
      <span class="comment">{{ description }}</span>
    </div>
  </div>
</template>

<style scoped lang="scss">
$link-box-size: 3rem;

.linkContainer {
  display: flex;
  gap: $link-box-size / 2;

  height: $link-box-size;
  margin: $link-box-size / 3 0;

  font-size: $link-box-size / 3;

  cursor: pointer;

  .linkBox {
    display: flex;

    width: $link-box-size / 3;
    height: $link-box-size / 3;

    padding: $link-box-size / 3;

    background-color: #E8EDF5;
    border-radius: $link-box-size / 6;

    img {
      width: $link-box-size / 3;
      height: $link-box-size / 3;
    }
  }

  .linkInfo {
    display: flex;
    flex-direction: column;
  }
}
</style>