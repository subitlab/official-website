import { createApp } from 'vue';
import '@/style.css';
import App from '@/App.vue';
import {createRouter, createWebHistory} from "vue-router";
import IndexView from "@/routes/IndexView.vue";
import AboutView from "@/routes/AboutView.vue";
import JoinView from "@/routes/JoinView.vue";
import AchievementsView from "@/routes/AchievementsView.vue";
import SupportView from "@/routes/SupportView.vue";

const routes = [
  { path: '/', component: IndexView },
  { path: '/about', component: AboutView },
  { path: '/join', component: JoinView },
  { path: '/achievements', component: AchievementsView },
  { path: '/support', component: SupportView }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App);
app.use(router);
app.mount('#app');
