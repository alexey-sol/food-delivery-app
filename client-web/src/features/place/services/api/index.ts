import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { transformGetItemsArg } from "@/shared/utils/converters";
import { getApiPath } from "@/shared/utils/formatters/api-path";
import { resources, url } from "@/shared/const";
import { type PlacePage } from "@/features/place/models";

import { createTag } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const baseUrl = getApiPath(url.API);

export const placeApi = createApi({
    reducerPath: "placeApi",
    tagTypes: [cn.PLACE_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getPlacesByLocalityId: builder.query<PlacePage, tp.GetPlacesByIdArg>({
            query: (arg) => ({
                params: transformGetItemsArg(arg),
                url: `${resources.LOCALITY}/${arg.localityId}/${resources.PLACE}`,
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
    useGetPlacesByLocalityIdQuery,
} = placeApi;
