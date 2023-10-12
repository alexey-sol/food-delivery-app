import { merge } from "webpack-merge";
import { BundleAnalyzerPlugin } from "webpack-bundle-analyzer";

import config from "./config.prod";

export default merge(config, {
    plugins: [
        new BundleAnalyzerPlugin(),
    ],
});
