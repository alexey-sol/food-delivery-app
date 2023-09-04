import { appConfig } from "app/app-config";
import { getApiPath } from "shared/utils/formatters/api-path";
import * as cn from "./const";

const { apiPrefix } = appConfig;

export const baseUrl = getApiPath(apiPrefix);

export const createTag = (id: string | number = cn.TAG_LIST_ID): {
    id: string | number;
    type: typeof cn.PLACE_TAG_TYPE;
} => ({
    id,
    type: cn.PLACE_TAG_TYPE,
});
