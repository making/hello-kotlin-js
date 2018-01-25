var path = require("path");
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: path.resolve(__dirname, "build/classes/kotlin/main/min/hello-kotlin-js.js"),
    output: {
        path: path.resolve(__dirname, "build/web"),
        filename: "bundle.js"
    },
    resolve: {
        alias: {
            kotlin: path.resolve(__dirname, "build/classes/kotlin/main/min/kotlin.js")
        }
    },
    plugins: [
        new CopyWebpackPlugin([{ from: path.resolve(__dirname, "src/main/web") }])
    ]
};