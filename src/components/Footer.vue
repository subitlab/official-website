<script setup lang="ts">
</script>

<template>
  <footer class="footer">
    <div class="content">
      <div class="top">
        <div class="left">
          <span class="logo">SubIT</span>
          <p class="description">
            北大附中的技术爱好者聚集地，让每一个爱好者<br/>
            都能充分施展拳脚。
          </p>
          <RouterLink to="/join" class="joinBtn">
            加入我们
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M5 12H19M19 12L12 5M19 12L12 19" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
          </RouterLink>
        </div>
        <div class="right">
          <a href="https://github.com/subitlab" target="_blank">GitHub</a>
          <a href="https://engage.cloud.microsoft/main/users/eyJfdHlwZSI6IlVzZXIiLCJpZCI6IjE2OTE1MTEwODgifQ/" target="_blank">Yammer</a>
          <a href="mailto:contact@subit.org.cn">Email</a>
          <a href="https://pkuschool.yuque.com/subit" target="_blank">语雀</a>
        </div>
      </div>
      <div class="divider"></div>
      <div class="bottom">
        <span>Copyright © 2018-2025 . All rights reserved. 京ICP备2022023326号</span>
      </div>
    </div>
  </footer>
</template>

<style scoped lang="scss">
.footer {
  width: 100%;
  background-color: #161616;
  color: white;
  display: flex;
  justify-content: center;
  box-sizing: border-box;
  padding: 56px 0 36px;
}

.content {
  width: 90%;
  max-width: 1200px;
  display: flex;
  flex-direction: column;
}

.top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 60px;
  
  @media (max-width: 768px) {
    flex-direction: column;
    gap: 40px;
  }
}

.left {
  display: flex;
  flex-direction: column;
  
  .logo {
    font-family: 'JetBrains Mono', monospace;
    font-size: 48px;
    font-weight: 700;
    margin-bottom: 20px;
  }
  
  .description {
    font-size: 16px;
    line-height: 1.6;
    color: #E8EDF5;
    margin: 0 0 30px 0;
  }

  .joinBtn {
    display: inline-flex;
    align-items: center;
    justify-content: space-between;
    width: fit-content;
    padding: 10px 20px;
    border: 1.5px solid white;
    background-color: transparent;
    color: white;
    text-decoration: none;
    font-size: 16px;
    font-weight: 500;
    gap: 10px;
    transition: all 0.3s ease;
    
    &:hover {
      background-color: rgba(255, 255, 255, 0.1);
    }

    svg {
      margin-left: 5px;
    }
  }
}

.right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 15px;
  
  @media (max-width: 768px) {
    align-items: flex-start;
  }
  
  a {
    color: white;
    text-decoration: none;
    font-size: 16px;
    transition: opacity 0.3s;
    
    &:hover {
      opacity: 0.8;
    }
  }
}

.divider {
  width: 100%;
  height: 1px;
  background-color: #333;
  margin-bottom: 30px;
}

.bottom {
  font-size: 14px;
  color: #E8EDF5;
}

@media (max-width: 768px) {
  .footer { padding: 48px 0 32px; }
}
</style>
