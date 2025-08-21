<script setup lang="ts">
defineProps<{color: string, backgroundColor: string}>();
</script>

<template>
  <div class="separatorGroup">
    <div class="separator rect1"/>
    <div class="separator rect2"/>
    <div class="separator rect3"/>
    <div class="separator rect4"/>
  </div>
</template>

<style scoped lang="scss">
.separatorGroup {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 5vw;
  background-color: v-bind(backgroundColor);

  .separator {
    background-color: v-bind(color);
    &.rect1 {
      width: 24%;
      height: 80%;
    }
    &.rect2 {
      width: 35%;
      height: 100%;
    }
    &.rect3 {
      width: 22%;
      height: 60%;
    }
    &.rect4 {
      width: 19%;
      height: 20%;
    }
  }
}
</style>