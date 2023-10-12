import * as Joi from "joi";

type NodeEnv = NodeJS.ProcessEnv["NODE_ENV"];

const DEVELOPMENT: NodeEnv = "development";
const PRODUCTION: NodeEnv = "production";

// Each environment variable used either in Webpack config or in runtime, should
// be listed in the schema below.
export const envSchema = Joi.object({
    API_GATEWAY_HOST: Joi
        .string()
        .required(),
    API_GATEWAY_PORT: Joi
        .number()
        .required(),
    APP_NAME: Joi
        .string()
        .required(),
    CLIENT_WEB_HOST: Joi
        .string()
        .required(),
    CLIENT_WEB_PORT: Joi
        .number()
        .required(),
    CLIENT_WEB_WEBSOCKET_URL: Joi
        .string()
        .default("auto://0.0.0.0:0/ws"),
    NODE_ENV: Joi
        .string()
        .valid(DEVELOPMENT, PRODUCTION)
        .default(PRODUCTION),
});
