import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import type { StorePage } from "features/store/models";

import { appConfig } from "app/app-config";
import { createTag, baseUrl, transformPaging } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { storeResource } = appConfig;

export const storeApi = createApi({
    reducerPath: "storeApi",
    tagTypes: [cn.STORE_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getStores: builder.query<StorePage, tp.GetStoresArg | void>({
            query: (arg) => ({
                params: transformPaging(arg?.paging),
                url: storeResource,
            }),
            providesTags: (result) => {
                const tag = createTag();

                return result
                    ? [...result.content.map(({ id }) => ({ type: tag.type, id })), tag]
                    : [tag];
            },
        }),
    }),
});

export const {
    useGetStoresQuery,
} = storeApi;
