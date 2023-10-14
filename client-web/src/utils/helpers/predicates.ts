import { type HasHttpStatus } from "@/shared/models/props";

type NodeEnv = NodeJS.ProcessEnv["NODE_ENV"];

const PRODUCTION: NodeEnv = "production";

export const isProduction = (nodeEnv: NodeEnv): boolean =>
    nodeEnv === PRODUCTION;

export const hasHttpStatus = (value: unknown): value is HasHttpStatus => value instanceof Object
    && typeof (value as Record<string, unknown>).status === "number";
