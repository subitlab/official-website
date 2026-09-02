<script setup lang="ts">

</script>

<template>
  <div class="content">
    <slot></slot>
  </div>
</template>

<style scoped lang="scss">
$padding-desktop: var(--page-gutter);
$padding-tablet: 3vw;
$padding-mobile: 20px;
$content-max-width: var(--content-width);

.content {
  display: flex;
  flex-direction: column;

  margin: 0 auto;
  box-sizing: border-box;
  padding-top: 20px;
  padding-left: $padding-desktop;
  padding-right: $padding-desktop;
  max-width: $content-max-width;
  width: 100%;

  @media (max-width: 1023px) {
    padding-left: $padding-tablet;
    padding-right: $padding-tablet;
    width: 100%;
  }

  @media (max-width: 767px) {
    padding-left: $padding-mobile;
    padding-right: $padding-mobile;
    width: 100%;
  }
}
</style>
