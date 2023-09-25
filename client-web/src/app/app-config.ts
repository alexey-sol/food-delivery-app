import { InvalidConfigError } from "shared/errors";

const config = {
    apiPrefix: process.env.API_GATEWAY_PREFIX?.replace("/", "") ?? "api",
    appName: process.env.APP_NAME ?? "Food Delivery",
    authResource: process.env.AUTH_RESOURCE ?? "auth",
    cartResource: process.env.CART_RESOURCE ?? "cart",
    nodeEnv: process.env.NODE_ENV,
    orderResource: process.env.ORDER_RESOURCE ?? "order",
    placeResource: process.env.PLACE_RESOURCE ?? "place",
    productResource: process.env.PRODUCT_RESOURCE ?? "product",
    userResource: process.env.USER_RESOURCE ?? "user",
} as const;

type RawConfig = typeof config;
type Key = keyof RawConfig;
type Value = NonNullable<RawConfig[Key]>;
type AppConfig = Record<Key, Value>;

const isAppConfig = (item: unknown): item is AppConfig =>
    item instanceof Object
        && Object.values(item).every((value) => value !== undefined);

if (!isAppConfig(config)) {
    throw new InvalidConfigError("Config contains undefined values");
}

export const appConfig = config;
