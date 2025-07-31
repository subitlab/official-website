import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import * as path from "node:path";

// https://vite.dev/config/
export default defineConfig({
  resolve: {
    alias:  {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  plugins: [vue()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/assets/common";`
      }
    }
  }
});
