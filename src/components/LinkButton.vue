<script setup lang="ts">
const { href } = defineProps<{
  href?: string,
  type: 'normal' | 'primary'
}>();

function openInNewTab() {
  if (href == undefined) {
    return;
  }
  window.open(href, '_blank');
}
</script>

<template>
  <button :class="['linkButton', type]" @click="openInNewTab">
    <slot></slot>
  </button>
</template>

<style scoped lang="scss">
.linkButton {
  align-self: flex-start;

  height: 3em;
  padding: 0.5em 1em;
  border-radius: 0.5em;

  border: none;

  cursor: pointer;

  font-weight: 700;
}

.normal {
  background-color: #E8EDF5;
  color: black;
}

.primary {
  background-color: #3D99F5;
  color: white;
}
</style>