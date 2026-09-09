<script setup lang="ts">
import type {DeepReadonly} from "vue";
import type {MemberSection} from "@/content/siteContent";

defineProps<{
  sections: readonly DeepReadonly<MemberSection>[];
}>();
</script>

<template>
  <section class="member-list">
    <div v-for="section in sections" :key="section.title" class="member-section">
      <h2 class="section-title">{{ section.title }}</h2>
      <div v-for="session in section.sessions" :key="session.name" class="session-block">
        <h3 class="session-title">
          <span class="accent" aria-hidden="true"></span>
          {{ session.name }}
        </h3>
        <ul class="member-grid">
          <li v-for="member in session.members" :key="member.name" class="member-card">
            <span class="name">{{ member.name }}</span>
            <span class="house">{{ member.house }}</span>
          </li>
        </ul>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
$subit-blue: #0071d4;

.member-list {
  padding-bottom: 32px;
}

.member-section {
  margin-bottom: 48px;

  .section-title {
    font-size: 24px;
    font-weight: 600;
    line-height: 1.2;
    color: #000;
    margin: 0 0 20px;
    padding-bottom: 12px;
    border-bottom: 2px solid #e7e7e7;
  }
}

.session-block {
  margin-bottom: 32px;

  .session-title {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 20px;
    font-weight: 600;
    line-height: 1.2;
    color: #000;
    margin: 0 0 16px;

    .accent {
      width: 14px;
      height: 14px;
      background-color: $subit-blue;
      flex-shrink: 0;
    }
  }
}

.member-grid {
  list-style: none;
  margin: 0;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
  gap: 12px;

  .member-card {
    display: flex;
    flex-direction: column;
    gap: 6px;
    padding: 16px;
    background-color: #fff;
    border: 1px solid #e7e7e7;
    border-radius: 4px;
    transition: border-color 0.15s ease;

    &:hover {
      border-color: $subit-blue;
    }

    .name {
      font-size: 18px;
      font-weight: 600;
      line-height: 1.2;
      color: #000;
    }

    .house {
      font-size: 14px;
      color: #6d6d6d;
    }
  }
}

@media (max-width: 760px) {
  .member-grid {
    grid-template-columns: repeat(auto-fill, minmax(130px, 1fr));
    gap: 8px;
  }

  .member-card {
    padding: 12px;
  }
}
</style>