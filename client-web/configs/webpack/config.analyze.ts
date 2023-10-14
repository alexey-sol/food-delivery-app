import { merge } from "webpack-merge";
import { BundleAnalyzerPlugin } from "webpack-bundle-analyzer";

import prodConfig from "./config.prod";

export default merge(prodConfig(undefined, { mode: "production" }), {
    plugins: [
        new BundleAnalyzerPlugin(),
    ],
});
