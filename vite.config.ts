import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import * as path from "node:path";

export default defineConfig({
  base: './', // 关键：确保打包后的静态资源使用相对路径，避免二级目录 404
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  plugins: [vue()],
  server: {
    proxy: {
      "/content-api": {
        target: "http://127.0.0.1:8091",
        changeOrigin: true,
        rewrite: requestPath => requestPath.replace(/^\/content-api/, ""),
      },
      "/content/": {
        target: "http://127.0.0.1:8091",
        changeOrigin: true,
      },
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "@/assets/common";`
      }
    }
  }
});
