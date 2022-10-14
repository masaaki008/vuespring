const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
    transpileDependencies: true,
    outputDir: "../main/resources/static",
    devServer: {
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                ws: false,
                changeOrigin: true,
            },
        },
    },
    pages: {
        index: {
            entry: "src/main.js",
            title: "在庫引当システム",
        },
    },
});
