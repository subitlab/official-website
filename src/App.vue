<script setup lang="ts">
import Footer from "@/components/Footer.vue";
import SearchDialog from "@/components/SearchDialog.vue";
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import {loadSiteContent} from "@/content/siteContent";

const searchOpen = ref(false);
const mobileOpen = ref(false);
const route = useRoute();

onMounted(loadSiteContent);

watch(() => route.fullPath, () => {
  mobileOpen.value = false;
});
</script>
<template>
  <nav class="nav">
    <div class="navInner">
      <div class="left">
        <RouterLink to="/" aria-label="返回首页">
          <img class="logo" src="@/assets/SubIT-Normal.svg" alt="SubIT Logo"/>
        </RouterLink>
      </div>
      <button class="menuButton" type="button" :aria-expanded="mobileOpen" aria-label="打开导航菜单" @click="mobileOpen = !mobileOpen">
        <span></span><span></span><span></span>
      </button>
      <div class="right" :class="{ open: mobileOpen }">
        <RouterLink to="/join">
          <span>加入我们</span>
        </RouterLink>
        <RouterLink to="/achievements">
          <span>项目成就</span>
        </RouterLink>
        <RouterLink to="/support">
          <span>提供支持</span>
        </RouterLink>
        <RouterLink to="/submore">
          <span>SubMore</span>
        </RouterLink>
        <button class="searchButton" type="button" aria-label="搜索" @click="searchOpen = true">
          <img src="@/assets/search.svg" alt=""/>
        </button>
      </div>
    </div>
  </nav>
  <main class="main">
    <RouterView />
  </main>
  <Footer />
  <SearchDialog :open="searchOpen" @close="searchOpen = false"/>
</template>
<style scoped lang="scss">
$navbar-height: 64px;
$divider-color: #E8EDF5;
.nav {
  display: flex;

  position: fixed;
  top: 0;
  left: 0;
  z-index: 9999;
  width: 100%;
  height: $navbar-height;
  box-sizing: border-box;

  background-color: rgba(255, 255, 255, .96);

  border-bottom-color: $divider-color;
  border-bottom-width: 1px;
  border-bottom-style: solid;
  backdrop-filter: blur(14px);

  user-select: none;

  .navInner {
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    width: 100%;
    max-width: var(--shell-width);
    height: 100%;
    margin: 0 auto;
    padding: 0 var(--page-gutter);
  }

  .left { margin-left: 0; }

  .right {
    display: flex;
    align-items: center;
    margin-right: 0;
    gap: 6px;

    a {
      display: flex;
      align-items: center;

      width: auto;
      height: $navbar-height;
      padding: 0 14px;
      border-radius: 0;

      text-decoration: none;
      color: #161616;
      font-size: 15px;
      font-weight: 650;

      &:hover {
        background-color: rgba(0, 0, 0, 0.055);
      }

      &.router-link-active span { color: var(--brand-blue); }

      span {
        flex-grow: 1;

        text-align: center;
        font-weight: inherit;
      }
    }
  }
}

.logo {
  display: block;
  width: 68px;
  height: auto;
}

.searchButton, .menuButton {
  border: 0;
  background: transparent;
  cursor: pointer;
}

.searchButton {
  display: grid;
  place-items: center;
  width: 40px;
  height: 40px;
  margin-left: 4px;
  border-radius: 8px;

  &:hover { background: rgba(0, 0, 0, .055); }
  img { width: 20px; height: 20px; }
}

.menuButton { display: none; }

.main {
  display: flow-root;

  margin-top: $navbar-height;
  width: 100%;
  min-height: calc(100vh - $navbar-height);
}

@media (max-width: 760px) {
  .nav {
    .navInner { padding: 0 14px 0 18px; }

    .right {
      position: absolute;
      top: $navbar-height;
      left: 0;
      right: 0;
      display: none;
      flex-direction: column;
      align-items: stretch;
      gap: 0;
      margin: 0;
      padding: 10px 14px 16px;
      border-bottom: 1px solid $divider-color;
      background: white;
      box-shadow: 0 12px 24px rgba(13, 20, 28, .08);

      &.open { display: flex; }

      a {
        box-sizing: border-box;
        width: 100%;
        height: 48px;
        padding: 0;
        border-radius: 8px;
        span { padding-left: 14px; text-align: left; }
      }
    }
  }

  .menuButton {
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 5px;
    width: 48px;
    margin-right: 0;

    span { width: 22px; height: 2px; margin: 0 auto; background: #0d141c; }
  }

  .searchButton { width: 100%; height: 44px; margin: 4px 0 0; justify-content: start; padding-left: 14px; }
}
</style>
