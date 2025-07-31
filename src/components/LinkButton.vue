<script setup lang="ts">
const { href } = defineProps<{
  href: string
}>();

function openInNewTab() {
  window.open(href, '_blank');
}
</script>

<template>
  <button class="linkButton" @click="openInNewTab">
    <slot></slot>
  </button>
</template>

<style scoped lang="scss">
.linkButton {
  height: 3em;
  padding: 0.5em 1em;
  border-radius: 0.5em;

  border: none;

  background-color: #3D99F5;
  color: white;

  cursor: pointer;

  font-weight: 700;
}
</style>