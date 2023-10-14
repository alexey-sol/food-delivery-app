import path from "path";

import HtmlWebpackPlugin from "html-webpack-plugin";
import DotenvWebpack from "dotenv-webpack";
import type webpack from "webpack";

import { getRules } from "./utils/rules";
import * as cn from "./const";

const cwd = process.cwd();
const publicDir = path.join(cwd, "src", "public");

const config: webpack.Configuration = {
    context: cwd,
    entry: path.resolve(cwd, "src"),
    module: {
        rules: getRules(),
    },
    output: {
        assetModuleFilename: `${cn.MEDIA_OUTPUT}/[name].[${cn.HASH}][ext]`,
        clean: true,
        path: path.resolve(cwd, "dist"),
    },
    resolve: {
        alias: {
            "@": path.join(cwd, "src"),
        },
        extensions: [".js", ".jsx", ".ts", ".tsx", ".css", ".scss"],
        modules: [cwd, "node_modules"],
    },
    plugins: [
        new DotenvWebpack({
            systemvars: true,
        }),
        new HtmlWebpackPlugin({
            template: path.join(publicDir, "index.html"),
        }),
    ],
    target: "web",
};

export default config;
