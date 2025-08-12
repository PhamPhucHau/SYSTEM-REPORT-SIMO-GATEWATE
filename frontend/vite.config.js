import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  allowedHosts: [ 'localhost', '127.0.0.1', '3ddf6adf9e15.ngrok-free.app'],
  server: {
    proxy: {
      "/simo": {
        target: `http://localhost:8081`,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/simo/, ""),
      }
    },
    host: true,
    port: 5173,
    strictPort: true,
  },
  preview: {
    allowedHosts: [ 'localhost', '127.0.0.1', '3ddf6adf9e15.ngrok-free.app'],
    proxy: {
      "/simo": {
        target: `http://localhost:8081`,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/simo/, ""),
      },
    },
    host: true,
    port: 4173,
    strictPort: true,
  }
})