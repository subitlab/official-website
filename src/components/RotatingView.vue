<script setup lang="ts">
import {ref, onMounted, onBeforeUnmount, type Component} from "vue"

const { components } = defineProps<{
  components: Component[]
}>();
const currentIndex = ref(0)
let timer!: number;

onMounted(() => {
  timer = setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % components.length
  }, 5000);
});

onBeforeUnmount(() => {
  clearInterval(timer);
});
</script>

<template>
  <div class="carousel-container">
    <transition-group name="slide" tag="div" class="carousel-inner">
      <component
          :is="components[currentIndex]"
          :key="currentIndex"
          class="carousel-item"
      />
    </transition-group>
  </div>
</template>

<style scoped lang="scss">
.carousel-container {
  position: relative;
  width: 100%;
  height: 100vh;
}

.carousel-inner {
  position: relative;
  width: 100%;
  height: 100%;
}

.carousel-item {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.8s ease, opacity 0.8s ease;
}

.slide-enter-from {
  transform: translateX(100%);
  opacity: 0;
}
.slide-enter-to {
  transform: translateX(0%);
  opacity: 1;
}

.slide-leave-from {
  transform: translateX(0%);
  opacity: 1;
}
.slide-leave-to {
  transform: translateX(-100%);
  opacity: 0;
}
</style>