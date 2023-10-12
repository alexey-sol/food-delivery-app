import path from "path";

import webpack from "webpack";

import * as cn from "../const";

const BABEL_CONFIG = path.join(__dirname, "..", "..", "babel", "config.base.js");

const getBabelLoaderRule = (configFile = BABEL_CONFIG) => ({
    test: /\.tsx?$/,
    loader: "babel-loader",
    exclude: [/node_modules/, /json/],
    options: {
        cacheDirectory: true,
        configFile,
    },
});

export const getMediaRule = () => ({
    test: /\.(png|jpe?g|gif)$/i,
    type: "asset/resource",
    generator: {
        filename: `${cn.MEDIA_OUTPUT}/[name][ext]`,
    },
} as const);

export const getRules = (babelConfig?: string): webpack.RuleSetRule[] => ([
    getBabelLoaderRule(babelConfig),
    getMediaRule(),
]);
