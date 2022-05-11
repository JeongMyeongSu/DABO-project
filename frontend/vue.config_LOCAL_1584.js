const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  outputDir: '../backend/src/main/resources/dist',
  transpileDependencies: true,
  // devServer: {
  //   proxy: {
  //     '/api': {
  //       target: "http://localhost:9090",
  //     }
  //   }
  // }
})
