<script setup lang="ts">

</script>

<template>
  <div class="content">
    <slot></slot>
  </div>
</template>

<style scoped lang="scss">
$padding-desktop: 6vw;
$padding-tablet: 3vw;
$padding-mobile: 0vw;
$content-max-width: 1200px;

.content {
  display: flex;
  flex-direction: column;

  margin: 0 auto;
  padding-left: $padding-desktop;
  padding-right: $padding-desktop;
  max-width: $content-max-width;
  width: calc(100% - 2 * $padding-desktop);

  @media (max-width: 1023px) {
    padding-left: $padding-tablet;
    padding-right: $padding-tablet;
    width: calc(100% - 2 * $padding-tablet);
  }

  @media (max-width: 767px) {
    padding-left: $padding-mobile;
    padding-right: $padding-mobile;
    width: calc(100% - 2 * $padding-mobile);
  }
}
</style>