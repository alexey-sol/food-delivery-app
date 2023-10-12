import path from "path";

import webpack from "webpack";
import merge from "webpack-merge";
import { CleanWebpackPlugin } from "clean-webpack-plugin";

import * as cn from "./const";
import coreConfig from "./config.base";

const config: webpack.Configuration = merge(coreConfig, {
    mode: "production",
    output: {
        filename: `${cn.JS_OUTPUT}/[name].[${cn.HASH}].js`,
        chunkFilename: `${cn.JS_OUTPUT}/[name].[${cn.HASH}].chunk.js`,
    },
    plugins: [
        new CleanWebpackPlugin(),
    ],
    devtool: "source-map",
    cache: false,
    optimization: {
        splitChunks: {
            chunks: "all",
            maxInitialRequests: Infinity,
            cacheGroups: {
                formsVendor: {
                    test: /[\\/]node_modules[\\/](formik)[\\/]/,
                    filename: path.join("js", "libs", "vendor.forms.js"),
                },
                vendor: {
                    test: /[\\/]node_modules[\\/](?!formik)(.[a-zA-Z0-9.\-_]+)[\\/]/,
                    filename: path.join(cn.JS_OUTPUT, "libs", "vendor.[name].js"),
                },
            },
        },
    },
});

export default config;
