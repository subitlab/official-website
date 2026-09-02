<script setup lang="ts">
import {ref, onMounted, onBeforeUnmount} from "vue"
import ProjectShowcase from "@/components/ProjectShowcase.vue";
import type {ProjectItem} from "@/content/siteContent";

const props = defineProps<{
  items: readonly ProjectItem[]
}>();
const currentIndex = ref(0)
const paused = ref(false);
const direction = ref<'next' | 'previous'>('next');
let timer: ReturnType<typeof setInterval> | undefined;

function select(index: number, requestedDirection?: 'next' | 'previous') {
  direction.value = requestedDirection ?? (index < currentIndex.value ? 'previous' : 'next');
  currentIndex.value = (index + props.items.length) % props.items.length;
  restart();
}

function restart() {
  if (timer) clearInterval(timer);
  timer = setInterval(() => {
    if (!paused.value) {
      direction.value = 'next';
      currentIndex.value = (currentIndex.value + 1) % props.items.length;
    }
  }, 5000);
}

onMounted(() => {
  restart();
});

onBeforeUnmount(() => {
  if (timer) clearInterval(timer);
});
</script>

<template>
  <div class="carousel-container" @mouseenter="paused = true" @mouseleave="paused = false" @focusin="paused = true" @focusout="paused = false">
    <transition-group :name="`slide-${direction}`" tag="div" class="carousel-inner">
      <ProjectShowcase
          :item="props.items[currentIndex]"
          :key="currentIndex"
          class="carousel-item"
      />
    </transition-group>
    <button class="arrow previous" type="button" aria-label="上一个项目" @click="select(currentIndex - 1, 'previous')">‹</button>
    <button class="arrow next" type="button" aria-label="下一个项目" @click="select(currentIndex + 1, 'next')">›</button>
    <div class="dots" aria-label="选择项目">
      <button
        v-for="(item, index) in props.items"
        :key="index"
        type="button"
        :class="{ active: index === currentIndex }"
        :aria-label="`查看${item.title}`"
        :aria-current="index === currentIndex ? 'true' : undefined"
        @click="select(index)"
      />
    </div>
  </div>
</template>

<style scoped lang="scss">
.carousel-container {
  position: relative;
  width: 100%;
  height: 590px;
  min-height: 0;
  overflow: hidden;
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

.slide-next-enter-active,
.slide-next-leave-active,
.slide-previous-enter-active,
.slide-previous-leave-active {
  transition: transform 0.8s ease, opacity 0.8s ease;
}

.slide-next-enter-from {
  transform: translateX(100%);
  opacity: 0;
}
.slide-next-enter-to,
.slide-previous-enter-to {
  transform: translateX(0%);
  opacity: 1;
}

.slide-next-leave-from,
.slide-previous-leave-from {
  transform: translateX(0%);
  opacity: 1;
}
.slide-next-leave-to {
  transform: translateX(-100%);
  opacity: 0;
}

.slide-previous-enter-from {
  transform: translateX(-100%);
  opacity: 0;
}

.slide-previous-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

.arrow {
  position: absolute;
  z-index: 4;
  top: 50%;
  display: grid;
  place-items: center;
  width: 44px;
  height: 44px;
  border: 1px solid rgba(13, 20, 28, .12);
  border-radius: 50%;
  background: rgba(255, 255, 255, .9);
  color: #0d141c;
  box-shadow: 0 6px 22px rgba(13, 20, 28, .11);
  cursor: pointer;
  font-size: 32px;
  line-height: 1;

  &.previous { left: max(24px, calc((100% - var(--content-width)) / 2)); }
  &.next { right: max(24px, calc((100% - var(--content-width)) / 2)); }
}

.dots {
  position: absolute;
  z-index: 4;
  left: 50%;
  bottom: 24px;
  display: flex;
  gap: 9px;
  transform: translateX(-50%);

  button {
    width: 9px;
    height: 9px;
    padding: 0;
    border: 0;
    border-radius: 999px;
    background: #a9b2bc;
    cursor: pointer;
    transition: width .2s ease, background .2s ease;

    &.active { width: 28px; background: #0066cc; }
  }
}

@media (max-width: 700px) {
  .carousel-container { height: 520px; min-height: 520px; }
  .arrow { width: 38px; height: 38px; top: 56%; }
  .arrow.previous { left: 10px; }
  .arrow.next { right: 10px; }
}

@media (prefers-reduced-motion: reduce) {
  .slide-next-enter-active,
  .slide-next-leave-active,
  .slide-previous-enter-active,
  .slide-previous-leave-active {
    transition: none;
  }
}
</style>
