<script setup lang="ts">
import Content from "@/components/Content.vue";
import {useSiteContent} from "@/content/siteContent";

const siteContent = useSiteContent();
</script>

<template>
  <Content>
    <div class="header">
      <h1>SubMore</h1>
      <p>{{ siteContent.submore.intro }}</p>
    </div>

    <section>
      <h2>历任社长与铁锅纪念馆</h2>
      <div class="pot-grid">
        <div v-for="(pot, index) in siteContent.submore.pots" :key="index" class="pot-card">
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
        <div v-for="(photo, index) in siteContent.submore.photos" :key="index" class="photo-card">
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
        <img :src="siteContent.submore.wordcloud" alt="社团成员关键词词云" class="wordcloud" />
      </div>
      
      <div class="quotes-grid">
        <div v-for="(quote, index) in siteContent.submore.quotes" :key="index" class="quote-item">
          <p>{{ quote.text }}</p>
          <span class="author">—— {{ quote.author }}</span>
        </div>
      </div>
    </section>

  </Content>
</template>

<style scoped lang="scss">
.header {
  margin-bottom: 0;
  h1 {
    font-size: 32px;
    margin: 0 0 10px;
    font-weight: 700;
  }
  p {
    font-size: 16px;
    color: #333;
  }
}

section {
  margin-bottom: 0;

  h2 {
    font-size: 22px;
    font-weight: 700;
    margin: 20px 0 12px;
  }
}

.pot-grid {
  display: flex;
  gap: 30px;
  overflow-x: auto;
  padding-bottom: 20px;
  
  scrollbar-width: none; 
  &::-webkit-scrollbar {
    display: none;
  }

  .pot-card {
    min-width: 180px;
    background: #E5E7EB;
    border-radius: 10px;
    overflow: hidden;
    
    .image-wrapper {
        height: 180px;
        background: white;
        margin: 0;
        border: 4px solid #e5e7eb;
        border-radius: 10px 10px 0 0;
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
      min-height: 64px;
      padding: 12px 10px;
      
      h3 {
        font-size: 20px;
        font-weight: 700;
        margin: 0 0 5px 0;
      }
      
      span {
        font-size: 12px;
        color: #666;
      }
    }
  }
}

.photo-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 30px;
  overflow-x: auto;
  padding-bottom: 12px;

  @media (max-width: 1024px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }

  .photo-card {
    min-width: 243px;
    .image-wrapper {
        aspect-ratio: 1;
        overflow: hidden;
        margin-bottom: 0;
        border-radius: 4px;
        background: #f0f0f0;
        
        img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .description {
      font-size: 12px;
      color: #888;
      display: block;
      text-align: center;
    }
  }
}

.wordcloud-container {
    display: flex;
    justify-content: center;
    margin-bottom: 34px;
    
    .wordcloud {
        max-width: 100%;
        height: auto;
    }
}

.quotes-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px 120px;
  padding-bottom: 54px;

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
