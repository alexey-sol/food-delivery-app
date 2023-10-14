import path from "path";

import merge from "webpack-merge";
import ForkTsCheckerWebpackPlugin from "fork-ts-checker-webpack-plugin";
import { config as dotenvConfig } from "dotenv";
import { type CallableOption } from "webpack-cli";

import baseConfig from "./config.base";
import { validateEnv } from "./env";
import * as cn from "./const";

const API_PREFIX = "/api";

const cwd = process.cwd();

const config: CallableOption = () => {
    dotenvConfig({ path: ".env.dev" });

    const env = validateEnv();

    return merge(baseConfig, {
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
                webSocketURL: env.stub.webSocketUrl,
            },
            compress: true,
            devMiddleware: {
                writeToDisk: false,
            },
            historyApiFallback: true,
            host: env.stub.host,
            port: env.stub.port,
            hot: true,
            proxy: {
                [API_PREFIX]: env.stub.apiUrl,
            },
            static: {
                directory: path.join(cwd, "src", "public"),
            },
        },
    });
};

export default config;
