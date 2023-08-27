import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";
import type { User } from "features/user/models";

import { baseUrl } from "./utils";

const { userResource } = appConfig;

export const userApi = createApi({
    reducerPath: "userApi",
    // tagTypes: [cn.CART_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getUserById: builder.query<User, number>({
            query: (userId) => ({
                url: `${userResource}/${userId}`,
            }),
        }),
    }),
});

export const {
    useGetUserByIdQuery,
} = userApi;
