import { InvalidConfigError } from "shared/errors";

const config = {
    apiPrefix: process.env.GATEWAY_PREFIX?.replace("/", "") ?? "api",
    appName: process.env.APP_NAME ?? "Food Delivery", // TODO const
    nodeEnv: process.env.NODE_ENV,
    storeResource: process.env.STORE_RESOURCE ?? "store",
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
