import { envSchema } from "./env.validation";

const { error, value } = envSchema.validate(process.env, {
    stripUnknown: true,
});

if (error) {
    throw error;
}

export const env = {
    apiPrefix: "api",
    appName: value.APP_NAME,
    nodeEnv: value.NODE_ENV,
    stub: {
        apiUrl: `http://${value.API_GATEWAY_HOST}:${value.API_GATEWAY_PORT}`,
        host: value.CLIENT_WEB_HOST,
        port: parseInt(value.CLIENT_WEB_PORT, 10),
        webSocketUrl: value.CLIENT_WEB_WEBSOCKET_URL,
    },
} as const;
