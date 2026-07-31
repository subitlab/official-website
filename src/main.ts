import { createApp } from 'vue';
import '@/style.css';
import App from '@/App.vue';
import {createRouter, createWebHistory} from "vue-router";
import hljsVuePlugin from "@highlightjs/vue-plugin";
import hljs from 'highlight.js/lib/core';
import javascript from 'highlight.js/lib/languages/javascript';
import IndexView from "@/routes/IndexView.vue";
import JoinView from "@/routes/JoinView.vue";
import AchievementsView from "@/routes/AchievementsView.vue";
import SupportView from "@/routes/SupportView.vue";
import SubMoreView from "@/routes/SubMoreView.vue";
import ContentEditorView from "@/routes/ContentEditorView.vue";

const routes = [
  { path: '/', component: IndexView },
  { path: '/submore', component: SubMoreView },
  { path: '/join', component: JoinView },
  { path: '/achievements', component: AchievementsView },
  { path: '/achievements/all', component: AchievementsView },
  { path: '/support', component: SupportView },
  { path: '/content-editor', component: ContentEditorView }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to) {
    if (to.hash) return {el: to.hash, behavior: 'smooth', top: 65};
    return {top: 0};
  },
});

hljs.registerLanguage('javascript', javascript);

const app = createApp(App);
app.use(router);
app.use(hljsVuePlugin);
app.mount('#app');
