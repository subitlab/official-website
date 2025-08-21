import { createApp } from 'vue';
import '@/style.css';
import App from '@/App.vue';
import {createRouter, createWebHistory} from "vue-router";
import hljsVuePlugin from "@highlightjs/vue-plugin";
import hljs from 'highlight.js/lib/core';
import javascript from 'highlight.js/lib/languages/javascript';
import IndexView from "@/routes/IndexView.vue";
import AboutView from "@/routes/AboutView.vue";
import JoinView from "@/routes/JoinView.vue";
import AchievementsView from "@/routes/AchievementsView.vue";
import SupportView from "@/routes/SupportView.vue";
import SSubITO from "@/routes/achievements/SSubITO.vue";
import Explore5 from "@/routes/achievements/Explore5.vue";
import YouthWrite from "@/routes/achievements/YouthWrite.vue";

const routes = [
  { path: '/', component: IndexView },
  { path: '/about', component: AboutView },
  { path: '/join', component: JoinView },
  { path: '/achievements', component: AchievementsView },
  { path: '/achievements/ssubito', component: SSubITO },
  { path: '/achievements/explore5', component: Explore5 },
  { path: '/achievements/youthwrite', component: YouthWrite },
  { path: '/support', component: SupportView }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

hljs.registerLanguage('javascript', javascript);

const app = createApp(App);
app.use(router);
app.use(hljsVuePlugin);
app.mount('#app');
