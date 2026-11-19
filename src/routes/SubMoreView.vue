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

    <section v-if="siteContent.submore.memberProfiles?.length" class="personas">
      <div class="section-heading">
        <div>
          <span class="eyebrow">PEOPLE / 成员</span>
          <h2>成员个性介绍</h2>
        </div>
        <p>技术能力写在项目里，性格写在自我介绍里。以下文字来自成员本人，保留了他们最原本的语气。</p>
      </div>
      <div class="persona-grid">
        <article v-for="(profile, index) in siteContent.submore.memberProfiles" :key="profile.name" class="persona-card">
          <div v-if="profile.image" class="portrait">
            <img :src="profile.image" :alt="`${profile.name}的照片`"/>
            <span>0{{ index + 1 }}</span>
          </div>
          <div v-else class="portrait portrait-fallback" role="img" :aria-label="`${profile.name}的头像`">
            <b>{{ profile.name.slice(-2) }}</b>
            <span>0{{ index + 1 }}</span>
          </div>
          <div class="persona-info">
            <span class="cohort">{{ profile.cohort }}</span>
            <h3>{{ profile.name }}</h3>
            <strong>{{ profile.role }}</strong>
            <p>{{ profile.description }}</p>
          </div>
        </article>
      </div>
    </section>

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
      <h2>社员语录</h2>
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

.personas {
  margin-top: 46px;
  margin-bottom: 54px;
  padding: 34px;
  border: 1px solid #e2e9f2;
  border-radius: 14px;
  background:
    radial-gradient(circle at 100% 0%, rgba(20, 123, 209, .11), transparent 30%),
    #f7f9fc;
}

.section-heading {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 48px;
  margin-bottom: 26px;

  .eyebrow {
    color: #147bd1;
    font-family: "JetBrains Mono", monospace;
    font-size: 11px;
    font-weight: 700;
    letter-spacing: .12em;
  }

  h2 { margin: 8px 0 0; font-size: 28px; }

  > p {
    max-width: 460px;
    margin: 0 0 2px;
    color: #64717d;
    font-size: 14px;
    line-height: 1.75;
  }
}

.persona-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.persona-card {
  display: flex;
  min-width: 0;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid rgba(13, 20, 28, .08);
  border-radius: 10px;
  background: white;
  box-shadow: 0 1px 2px rgba(13, 20, 28, .04);
  transition: border-color .2s, box-shadow .2s, transform .2s;

  &:hover {
    border-color: rgba(20, 123, 209, .42);
    box-shadow: 0 18px 34px -24px rgba(13, 20, 28, .35);
    transform: translateY(-3px);
  }
}

.portrait {
  position: relative;
  overflow: hidden;
  aspect-ratio: 1;
  background: #dfe8f2;

  img {
    display: block;
    width: 100%;
    height: 100%;
    object-fit: cover;
    object-position: center 32%;
    transition: transform .45s ease;
  }

  > span {
    position: absolute;
    top: 12px;
    left: 12px;
    padding: 3px 7px;
    border-radius: 2px;
    background: rgba(13, 20, 28, .72);
    color: white;
    font-family: "JetBrains Mono", monospace;
    font-size: 10px;
    letter-spacing: .08em;
    backdrop-filter: blur(6px);
  }

  .persona-card:hover & img { transform: scale(1.025); }
}

.portrait-fallback {
  display: grid;
  place-items: center;
  background:
    radial-gradient(circle at 80% 15%, rgba(41, 144, 235, .32), transparent 34%),
    #161616;

  b {
    color: white;
    font-size: 42px;
    letter-spacing: -.04em;
  }
}

.persona-info {
  display: flex;
  flex: 1;
  flex-direction: column;
  padding: 18px 18px 20px;

  .cohort {
    color: #7b8792;
    font-family: "JetBrains Mono", monospace;
    font-size: 10.5px;
    letter-spacing: .08em;
  }

  h3 {
    margin: 5px 0 3px;
    color: #0d141c;
    font-size: 22px;
    line-height: 1.25;
  }

  strong {
    color: #147bd1;
    font-size: 12px;
    font-weight: 700;
    letter-spacing: .02em;
  }

  p {
    margin: 14px 0 0;
    color: #5b6670;
    font-size: 13.5px;
    line-height: 1.72;
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
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  padding-bottom: 12px;

  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }

  .photo-card {
    min-width: 0;
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
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
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

  .quote-item:only-child {
    padding: 28px 32px;
    border-left: 4px solid #147bd1;
    border-radius: 0 8px 8px 0;
    background: #f7f9fc;

    p { margin-bottom: 12px; font-size: 20px; font-weight: 600; }
  }
}

@media (max-width: 1024px) {
  .persona-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }

  .section-heading {
    align-items: flex-start;
    flex-direction: column;
    gap: 14px;

    > p { max-width: 640px; }
  }
}

@media (max-width: 760px) {
  .personas {
    margin-top: 32px;
    margin-bottom: 42px;
    padding: 24px 18px;
  }

  .persona-grid { grid-template-columns: 1fr; }
  .portrait { aspect-ratio: 4 / 3; }
  .quote-item:only-child { padding: 22px 20px; }
}
</style>
