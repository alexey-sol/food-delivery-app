import { appConfig } from "app/app-config";
import { getApiPath } from "shared/utils/formatters/api-path";
import { PagingOptions } from "shared/models";
import * as cn from "./const";
import type { GetStoresArg } from "./types";

const { apiPrefix } = appConfig;

const PAGE_OFFSET = 1;

export const baseUrl = getApiPath(apiPrefix); // TODO apiPrefix

export const transformPaging = (paging?: GetStoresArg["paging"]): Pick<PagingOptions, "size" | "page"> | undefined => {
    if (paging) {
        return {
            ...paging,
            page: paging.page - PAGE_OFFSET,
        };
    }

    return undefined;
};

export const createTag = (id: string | number = cn.TAG_LIST_ID): {
    id: string | number;
    type: typeof cn.STORE_TAG_TYPE;
} => ({
    id,
    type: cn.STORE_TAG_TYPE,
});
