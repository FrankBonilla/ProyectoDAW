module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy: {
      '^/api': {
        target: 'https://localhost:8080/',
        changeOrigin: true,
        secure: false,
        pathRewrite: {"^/api":"/"}
      }
    }
}
}
