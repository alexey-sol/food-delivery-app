import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "@/app/app-config";
import { transformGetItemsArg } from "@/shared/utils/converters";
import type { PlacePage } from "@/features/place/models";

import { createTag, baseUrl } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { placeResource } = appConfig;

const LOCALITY = "locality"; // TODO shared const

export const placeApi = createApi({
    reducerPath: "placeApi",
    tagTypes: [cn.PLACE_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getPlacesByLocalityId: builder.query<PlacePage, tp.GetPlacesByIdArg>({
            query: (arg) => ({
                params: transformGetItemsArg(arg),
                url: `${LOCALITY}/${arg.localityId}/${placeResource}`,
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
