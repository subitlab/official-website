<script setup lang="ts">
import Content from "@/components/Content.vue";

const pots = [
  {
    image: "/pot_mk1.png",
    title: "铁锅 Mk.1",
    subtitle: "2021届社长 | 刘语辰"
  },
  {
    image: "/pot_mk2.png",
    title: "铁锅 Mk.2",
    subtitle: "2022届社长 | 何天阳"
  },
  {
    image: "/pot_mk3.png",
    title: "铁锅 Mk.3",
    subtitle: "2023届社长 | 刘宇宸"
  },
  {
    image: "/pot_mk4.png",
    title: "铁锅 Mk.4",
    subtitle: "2024届社长 | 魏子峰"
  },
  {
    image: "/pot_mk5.png",
    title: "铁锅 Mk.5",
    subtitle: "2021届社长 | 刘语辰"
  }
];

const photos = [
  {
    image: "/photo_1.png",
    description: "社活一角 | 脆皮四绘于 2025 年夏"
  },
  {
    image: "/photo_1.png",
    description: "社活一角 | 脆皮四绘于 2025 年夏"
  },
  {
    image: "/photo_1.png",
    description: "社活一角 | 脆皮四绘于 2025 年夏"
  },
  {
    image: "/photo_1.png",
    description: "社活一角 | 脆皮四绘于 2025 年夏"
  }
];

const quotes = [
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  },
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  },
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  },
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  },
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  },
  {
    text: "赛博旺证遗民这辈子也就这样了，鉴定为屁股决定上限",
    author: "YJN"
  }
];
</script>

<template>
  <Content>
    <div class="header">
      <h1>SubMore</h1>
      <p>在 SubIT，除了常规的社团活动，我们还有各种奇妙的社团文化 ......</p>
    </div>

    <section>
      <h2>历任社长与铁锅纪念馆</h2>
      <div class="pot-grid">
        <div v-for="(pot, index) in pots" :key="index" class="pot-card">
          <div class="image-wrapper">
             <img :src="pot.image" :alt="pot.title" />
          </div>
          <div class="info">
            <h3>{{ pot.title }}</h3>
            <span>{{ pot.subtitle }}</span>
          </div>
        </div>
      </div>
    </section>

    <section>
      <h2>珍藏影相</h2>
      <div class="photo-grid">
        <div v-for="(photo, index) in photos" :key="index" class="photo-card">
          <div class="image-wrapper">
            <img :src="photo.image" :alt="photo.description" />
          </div>
          <span class="description">{{ photo.description }}</span>
        </div>
      </div>
    </section>

    <section>
      <h2>我们想说（这个标题没想好）</h2>
      <div class="wordcloud-container">
        <img src="/wordcloud.png" alt="Word Cloud" class="wordcloud" />
      </div>
      
      <div class="quotes-grid">
        <div v-for="(quote, index) in quotes" :key="index" class="quote-item">
          <p>{{ quote.text }}</p>
          <span class="author">—— {{ quote.author }}</span>
        </div>
      </div>
    </section>

  </Content>
</template>

<style scoped lang="scss">
.header {
  margin-bottom: 60px;
  h1 {
    font-size: 48px;
    margin-bottom: 20px;
    font-weight: 700;
  }
  p {
    font-size: 18px;
    color: #333;
  }
}

section {
  margin-bottom: 80px;

  h2 {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 40px;
  }
}

.pot-grid {
  display: flex;
  gap: 20px;
  overflow-x: auto;
  padding-bottom: 20px;
  
  scrollbar-width: none; 
  &::-webkit-scrollbar {
    display: none;
  }

  .pot-card {
    min-width: 240px;
    background: #E8E8E8;
    border-radius: 12px;
    overflow: hidden;
    
    .image-wrapper {
        background: white;
        margin: 10px;
        border-radius: 8px;
        overflow: hidden;
        aspect-ratio: 1;
        display: flex;
        align-items: center;
        justify-content: center;
        
        img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .info {
      padding: 15px;
      
      h3 {
        font-size: 20px;
        font-weight: 700;
        margin: 0 0 5px 0;
      }
      
      span {
        font-size: 14px;
        color: #666;
      }
    }
  }
}

.photo-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;

  @media (max-width: 1024px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }

  .photo-card {
    .image-wrapper {
        aspect-ratio: 1;
        overflow: hidden;
        margin-bottom: 10px;
        border-radius: 8px;
        background: #f0f0f0;
        
        img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .description {
      font-size: 14px;
      color: #888;
      display: block;
      text-align: center;
    }
  }
}

.wordcloud-container {
    display: flex;
    justify-content: center;
    margin-bottom: 60px;
    
    .wordcloud {
        max-width: 100%;
        height: auto;
    }
}

.quotes-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px 80px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }

  .quote-item {
    p {
      font-size: 16px;
      margin-bottom: 10px;
      line-height: 1.6;
    }

    .author {
      font-size: 14px;
      color: #888;
      display: block;
      text-align: right;
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>
