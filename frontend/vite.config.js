import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

const projectRootDir = path.resolve(__dirname);

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(projectRootDir, "src"),
     // '~assets': path.resolve(projectAssets, "src/assets"),
     // '~components': path.resolve(projectComponents, "src/assets"),
      '~bootstrap': path.resolve(__dirname, 'node_modules/bootstrap')
    }
  }
})
