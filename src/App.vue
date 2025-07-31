<script setup lang="ts">
</script>
<template>
  <nav class="nav">
    <div class="left">
      <RouterLink to="/">
        <img class="logo" src="@/assets/SubIT-Normal.svg" alt="SubIT Logo"/>
      </RouterLink>
    </div>
    <div class="right">
      <RouterLink to="/about">
        <span>关于我们</span>
      </RouterLink>
      <RouterLink to="/join">
        <span>加入我们</span>
      </RouterLink>
      <RouterLink to="/achievements">
        <span>项目成就</span>
      </RouterLink>
      <RouterLink to="/support">
        <span>提供支持</span>
      </RouterLink>
    </div>
  </nav>
  <main class="main">
    <RouterView />
  </main>
</template>
<style scoped lang="scss">
$navbar-height: 60px;
$divider-color: #E8EDF5;
.nav {
  display: flex;
  justify-content: space-between;

  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: $navbar-height;

  background-color: white;

  border-bottom-color: $divider-color;
  border-bottom-width: 2px;
  border-bottom-style: solid;

  user-select: none;

  div {
    display: flex;
    align-items: center;
    margin: 0 40px;
  }

  .right {
    a {
      display: flex;
      align-items: center;

      width: 100px;
      height: $navbar-height;

      text-decoration: none;
      color: black;

      &:hover {
        background-color: rgba(0, 0, 0, 0.1);
      }

      span {
        flex-grow: 1;

        text-align: center;
        font-weight: 500;
      }
    }
  }
}

.logo {
  height: $navbar-height - 10px;
}

.main {
  display: flow-root;

  margin-top: $navbar-height;
  width: 100%;
}
</style>