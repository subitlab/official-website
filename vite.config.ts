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
