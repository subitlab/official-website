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
$padding-mobile: 0;
$content-max-width: 1200px;

.content {
  margin: 0 auto;
  padding-left: $padding-desktop;
  padding-right: $padding-desktop;
  max-width: $content-max-width;
  width: 100%;

  @media (max-width: 1023px) {
    padding-left: $padding-tablet;
    padding-right: $padding-tablet;
  }

  @media (max-width: 767px) {
    padding-left: $padding-mobile;
    padding-right: $padding-mobile;
  }
}
</style>