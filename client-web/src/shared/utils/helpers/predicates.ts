import { type ApiError } from "@/shared/models";
import { type HasHttpStatus } from "@/shared/models/props";

const hasHttpStatus = (value: unknown): value is HasHttpStatus => value instanceof Object
    && typeof (value as Record<string, unknown>).status === "number";

export const isApiError = (value: unknown): value is ApiError => hasHttpStatus(value)
    && "detail" in value && "title" in value;
