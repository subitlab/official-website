<script setup lang="ts">

import Content from "@/components/Content.vue";
import Achievement from "@/components/Achievement.vue";
import LinkButton from "@/components/LinkButton.vue";
import {computed, ref} from "vue";
import {useRoute} from "vue-router";
import {useSiteContent} from "@/content/siteContent";

const route = useRoute();
const isAll = computed(() => route.path.endsWith('/all'));
const siteContent = useSiteContent();

type FilterKey = "all" | "running" | "archived" | "event";

const filters: Array<{key: FilterKey, label: string}> = [
  {key: "all", label: "全部"},
  {key: "running", label: "进行中"},
  {key: "archived", label: "已完成"},
  {key: "event", label: "活动类"},
];

const groups: Array<{key: Exclude<FilterKey, "all">, label: string}> = [
  {key: "running", label: "进行中"},
  {key: "archived", label: "已完成"},
  {key: "event", label: "活动类"},
];

const activeFilter = ref<FilterKey>("all");

const visibleGroups = computed(() => groups
  .filter((group) => activeFilter.value === "all" || activeFilter.value === group.key)
  .map((group) => ({
    key: group.key,
    label: group.label,
    items: siteContent.achievements.filter((achievement) => achievement.type === group.key),
  }))
  .filter((group) => group.items.length > 0));
</script>

<template>
  <Content>
    <h1>{{ isAll ? '所有项目与成就' : '项目与成就' }}</h1>
    <div class="filters" role="group" aria-label="按状态筛选项目">
      <button v-for="item in filters"
              :key="item.key"
              type="button"
              class="chip"
              :class="{ active: activeFilter === item.key }"
              :aria-pressed="activeFilter === item.key"
              @click="activeFilter = item.key">
        {{ item.label }}
      </button>
    </div>
    <template v-if="visibleGroups.length">
      <section v-for="group in visibleGroups" :key="group.key" class="group">
        <h2 class="groupTitle">{{ group.label }}<span>{{ group.items.length }}</span></h2>
        <div class="achievementsContainer">
          <Achievement v-for="(achievement, index) in group.items"
                       :key="`${group.key}-${index}`"
                       :image="achievement.image"
                       :title="achievement.title"
                       :description="achievement.description"
                       :href="achievement.href"
                       :type="achievement.type"/>
        </div>
      </section>
    </template>
    <p v-else class="empty">暂无此类项目</p>
    <RouterLink v-if="!isAll" class="checkAll" to="/achievements/all">
      <LinkButton type="normal">查看所有成就 >></LinkButton>
    </RouterLink>
    <RouterLink v-else class="checkAll" to="/achievements">
      <LinkButton type="normal">返回项目概览</LinkButton>
    </RouterLink>
  </Content>
</template>

<style scoped lang="scss">
h1 { margin: 0 0 20px; font-size: 32px; }

.filters {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 12px;
}

.chip {
  padding: 7px 16px;
  border: 1px solid #dfe5ec;
  border-radius: 999px;
  background: white;
  color: #4b5563;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: color .15s, border-color .15s, background-color .15s;

  &:hover { border-color: var(--brand-blue); color: var(--brand-blue); }

  &.active {
    border-color: var(--brand-blue);
    background: var(--brand-blue);
    color: white;
  }
}

.group + .group { margin-top: 36px; }

.groupTitle {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0 0 16px;
  font-size: 19px;

  span {
    padding: 1px 7px;
    border-radius: 999px;
    background: #f0f3f8;
    color: #6b7280;
    font-size: 12px;
    font-weight: 600;
  }
}

.achievementsContainer {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 301px));
  justify-content: start;
  gap: 24px 12px;
}

.empty {
  margin: 32px 0;
  color: #6b7280;
  font-size: 15px;
}

.checkAll {
  margin-top: 30px;
  margin-left: auto;
  margin-bottom: 30px;
}
</style>
