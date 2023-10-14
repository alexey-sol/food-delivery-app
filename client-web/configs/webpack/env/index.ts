import { envSchema } from "./env.validation";

export const validateEnv = () => {
    const { error, value } = envSchema.validate(process.env, {
        abortEarly: false,
        stripUnknown: true,
    });

    if (error) {
        throw error;
    }

    return {
        appName: value.APP_NAME,
        nodeEnv: value.NODE_ENV,
        stub: {
            apiUrl: `http://${value.API_GATEWAY_HOST}:${value.API_GATEWAY_PORT}`,
            host: value.CLIENT_WEB_HOST,
            port: parseInt(value.CLIENT_WEB_PORT, 10),
            webSocketUrl: value.CLIENT_WEB_WEBSOCKET_URL,
        },
    };
};
