import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { transformGetItemsArg } from "@/shared/utils/converters";
import { getApiPath } from "@/shared/utils/formatters/api-path";
import { resources, url } from "@/shared/const";
import { type ProductPage } from "@/features/product/models";

import { createTag } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const baseUrl = getApiPath(url.API);

export const productApi = createApi({
    reducerPath: "productApi",
    tagTypes: [cn.PRODUCT_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getProductsByPlaceId: builder.query<ProductPage, tp.GetProductsByPlaceIdArg>({
            query: (arg) => ({
                params: transformGetItemsArg(arg),
                url: `${resources.PLACE}/${arg.placeId}/${resources.PRODUCT}`,
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
    useGetProductsByPlaceIdQuery,
} = productApi;
