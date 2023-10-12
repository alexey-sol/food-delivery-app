import path from "path";

import webpack from "webpack";
import merge from "webpack-merge";
import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import { config as dotenvConfig } from "dotenv";
import type { Configuration as DevServerConfiguration } from "webpack-dev-server";

import * as cn from "./const";

dotenvConfig({ path: ".env.dev" }); // TODO not nice to keep it between imports

import { env as envCf } from "./env";
import baseConfig from "./config.base";

const cwd = process.cwd();

interface DevelopmentConfiguration extends webpack.Configuration {
    devServer?: DevServerConfiguration;
}

const config: DevelopmentConfiguration = merge(baseConfig, {
    mode: "development",
    output: {
        filename: `${cn.JS_OUTPUT}/bundle.js`,
        chunkFilename: `${cn.JS_OUTPUT}/[name].chunk.js`,
    },
    plugins: [
        new ForkTsCheckerWebpackPlugin({
            async: false,
            typescript: {
                configFile: "tsconfig.json",
                context: cwd,
                diagnosticOptions: {
                    declaration: true,
                    semantic: true,
                    syntactic: true,
                },
                mode: "write-references",
            },
        }),
    ],
    devtool: "inline-source-map",
    devServer: {
        client: {
            webSocketURL: envCf.stub.webSocketUrl,
        },
        compress: true,
        devMiddleware: {
            writeToDisk: false,
        },
        historyApiFallback: true,
        host: envCf.stub.host,
        port: envCf.stub.port,
        hot: true,
        proxy: {
            [`/${envCf.apiPrefix}`]: envCf.stub.apiUrl,
        },
        static: {
            directory: path.join(cwd, "src", "public"),
        },
    },
});

export default config;
