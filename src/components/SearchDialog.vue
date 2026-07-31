<script setup lang="ts">
import {computed, nextTick, onBeforeUnmount, onMounted, ref, watch} from "vue";
import {useSiteContent} from "@/content/siteContent";

const props = defineProps<{ open: boolean }>();
const emit = defineEmits<{ close: [] }>();

type SearchMode = "site" | "yuque";

const mode = ref<SearchMode>("site");
const query = ref("");
const input = ref<HTMLInputElement | null>(null);
const siteContent = useSiteContent();

interface SearchItem {
  id: string;
  title: string;
  description: string;
  to: string;
  keywords: string;
}

const searchIndex = computed<SearchItem[]>(() => [
  {id: "page-home", title: "首页", description: "了解 SubIT、社团职责与事业群", to: "/", keywords: "subit 首页 社团 技术爱好者"},
  {id: "page-responsibilities", title: "我们的职责", description: "放送、创造与助力", to: "/#responsibilities", keywords: "职责 放送 broadcasting 创造 creating 助力 helping"},
  {id: "page-groups", title: "不止于技术", description: "技术与支持事业群、平台与内容事业群", to: "/#groups", keywords: "thg pcg 技术 支持 平台 内容 事业群"},
  {id: "page-join", title: "加入我们", description: siteContent.join.intro, to: "/join", keywords: `加入 招新 社员 语雀 ${siteContent.join.membersTitle}`},
  {id: "page-achievements", title: "项目与成就", description: "查看 SubIT 的项目与活动成果", to: "/achievements", keywords: "项目 成就 github 运行中 已存档"},
  {id: "page-support", title: "技术支持", description: "预约线下或在线技术支持", to: "/support", keywords: "技术支持 本地 线下 在线 email 邮件"},
  {id: "page-submore", title: "SubMore", description: siteContent.submore.intro, to: "/submore", keywords: "submore 社团文化 铁锅 社长 照片 影相 语录"},
  ...siteContent.projects.map((item, index) => ({
    id: `project-${index}`,
    title: item.title,
    description: item.subtitle,
    to: "/#projects",
    keywords: `项目 ${item.linkLabel} ${item.href} ${item.imageAlt}`,
  })),
  ...siteContent.achievements.map((item, index) => ({
    id: `achievement-${index}`,
    title: item.title,
    description: item.description,
    to: "/achievements",
    keywords: `成就 ${item.type} ${item.href}`,
  })),
  ...siteContent.join.members.map((item, index) => ({
    id: `member-${index}`,
    title: item.name,
    description: item.description,
    to: "/join",
    keywords: `社员 成员 ${siteContent.join.membersTitle}`,
  })),
  ...siteContent.submore.pots.map((item, index) => ({
    id: `pot-${index}`,
    title: item.title,
    description: item.subtitle,
    to: "/submore",
    keywords: "SubMore 铁锅 纪念馆 社长",
  })),
  ...siteContent.submore.photos.map((item, index) => ({
    id: `photo-${index}`,
    title: `珍藏影相 ${index + 1}`,
    description: item.description,
    to: "/submore",
    keywords: "SubMore 照片 影相 社团活动",
  })),
  ...siteContent.submore.quotes.map((item, index) => ({
    id: `quote-${index}`,
    title: `成员语录 · ${item.author}`,
    description: item.text,
    to: "/submore",
    keywords: "SubMore 语录 成员 社团文化",
  })),
]);

const results = computed(() => {
  const value = query.value.trim().toLocaleLowerCase();
  if (!value) return searchIndex.value;
  return searchIndex.value.filter((item) =>
    `${item.title} ${item.description} ${item.keywords}`.toLocaleLowerCase().includes(value),
  );
});

watch(() => props.open, async (open) => {
  if (!open) return;
  await nextTick();
  input.value?.focus();
});

function submit() {
  const value = query.value.trim();
  if (mode.value === "yuque" && value) {
    window.open(`https://pkuschool.yuque.com/subit/search?q=${encodeURIComponent(value)}`, "_blank", "noopener,noreferrer");
  }
}

function onKeydown(event: KeyboardEvent) {
  if (event.key === "Escape" && props.open) emit("close");
}

onMounted(() => window.addEventListener("keydown", onKeydown));
onBeforeUnmount(() => window.removeEventListener("keydown", onKeydown));
</script>

<template>
  <Teleport to="body">
    <Transition name="search-fade">
      <div v-if="open" class="search-backdrop" role="presentation" @mousedown.self="emit('close')">
        <section class="search-dialog" role="dialog" aria-modal="true" aria-labelledby="search-title">
          <header>
            <div>
              <span class="eyebrow">SEARCH</span>
              <h2 id="search-title">搜索 SubIT</h2>
            </div>
            <button class="close" type="button" aria-label="关闭搜索" @click="emit('close')">×</button>
          </header>

          <div class="mode-switch" aria-label="搜索范围">
            <button type="button" :class="{ active: mode === 'site' }" @click="mode = 'site'">网站内</button>
            <button type="button" :class="{ active: mode === 'yuque' }" @click="mode = 'yuque'">SubIT 语雀库</button>
          </div>

          <form class="search-form" @submit.prevent="submit">
            <img src="@/assets/search.svg" alt="" />
            <input
              ref="input"
              v-model="query"
              type="search"
              :placeholder="mode === 'site' ? '搜索页面、项目或服务' : '搜索 SubIT 语雀知识库'"
              aria-label="搜索关键词"
            />
            <button v-if="mode === 'yuque'" type="submit" :disabled="!query.trim()">前往语雀</button>
          </form>

          <div v-if="mode === 'site'" class="results" aria-live="polite">
            <RouterLink v-for="item in results" :key="item.id" :to="item.to" @click="emit('close')">
              <strong>{{ item.title }}</strong>
              <span>{{ item.description }}</span>
            </RouterLink>
            <p v-if="results.length === 0" class="empty">没有找到相关内容，换个关键词试试。</p>
          </div>
          <p v-else class="yuque-hint">输入关键词后将在新标签页打开 SubIT 语雀知识库的搜索结果。</p>
        </section>
      </div>
    </Transition>
  </Teleport>
</template>

<style scoped lang="scss">
.search-backdrop {
  position: fixed;
  inset: 0;
  z-index: 10000;
  display: grid;
  place-items: start center;
  padding: 10vh 20px 20px;
  background: rgba(13, 20, 28, 0.52);
  backdrop-filter: blur(10px);
}

.search-dialog {
  width: min(680px, 100%);
  max-height: 80vh;
  overflow: auto;
  padding: 28px;
  border-radius: 18px;
  background: white;
  box-shadow: 0 24px 80px rgba(13, 20, 28, 0.24);

  header {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
  }

  h2 {
    margin: 2px 0 22px;
    font-size: 28px;
  }
}

.eyebrow {
  color: #4a739c;
  font-family: "JetBrains Mono", monospace;
  font-size: 12px;
  letter-spacing: .14em;
}

.close {
  width: 40px;
  height: 40px;
  border: 0;
  border-radius: 50%;
  background: #f1f5f9;
  color: #0d141c;
  cursor: pointer;
  font-size: 26px;
  line-height: 1;
}

.mode-switch {
  display: inline-flex;
  padding: 4px;
  border-radius: 10px;
  background: #eef3f8;

  button {
    padding: 9px 16px;
    border: 0;
    border-radius: 7px;
    background: transparent;
    color: #4a5d70;
    cursor: pointer;
    font: inherit;

    &.active {
      background: white;
      color: #0d141c;
      box-shadow: 0 2px 8px rgba(13, 20, 28, .1);
    }
  }
}

.search-form {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-top: 18px;
  padding: 10px 12px;
  border: 2px solid #d9e2ec;
  border-radius: 12px;

  &:focus-within {
    border-color: #2990eb;
    box-shadow: 0 0 0 3px rgba(41, 144, 235, .14);
  }

  img {
    width: 20px;
    height: 20px;
  }

  input {
    flex: 1;
    min-width: 0;
    border: 0;
    background: transparent;
    font: inherit;
  }

  button {
    padding: 9px 14px;
    border: 0;
    border-radius: 8px;
    background: #2990eb;
    color: white;
    cursor: pointer;

    &:disabled { opacity: .45; cursor: not-allowed; }
  }
}

.results {
  display: grid;
  gap: 6px;
  margin-top: 14px;

  a {
    display: flex;
    flex-direction: column;
    gap: 3px;
    padding: 12px 14px;
    border-radius: 10px;
    color: #0d141c;
    text-decoration: none;

    &:hover, &:focus-visible { background: #f3f7fb; }
    span { color: #597086; font-size: 14px; }
  }
}

.empty, .yuque-hint { margin: 20px 2px 4px; color: #597086; }
.search-fade-enter-active, .search-fade-leave-active { transition: opacity .18s ease; }
.search-fade-enter-from, .search-fade-leave-to { opacity: 0; }

@media (max-width: 600px) {
  .search-backdrop { padding: 0; place-items: stretch; }
  .search-dialog { width: auto; max-height: none; min-height: 100vh; border-radius: 0; padding: 24px 18px; }
}
</style>
