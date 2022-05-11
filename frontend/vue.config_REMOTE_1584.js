const { defineConfig } = require('@vue/cli-service')
const webpack = require("webpack")
const path = require("path")

module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../backend/bf/src/main/resources/dist',
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        Buffer: ["buffer", "Buffer"],
        process: "process/browser",
      }),
    ],
    resolve: {
      alias: {
        "@contracts": path.resolve(__dirname, "build", "contracts")
      },
      fallback: {
        http: require.resolve("stream-http"),
        https: require.resolve("https-browserify"),
        crypto: require.resolve("crypto-browserify"),
        stream: require.resolve("stream-browserify"),
        os: require.resolve("os-browserify/browser"),
        url: require.resolve("url"),
        assert: require.resolve("assert"),
      },
    },
  }
})