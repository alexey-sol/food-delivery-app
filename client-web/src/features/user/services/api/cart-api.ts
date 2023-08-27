import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";
import type { Cart } from "features/user/models";

import { baseUrl } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { cartResource } = appConfig;

export const cartApi = createApi({
    reducerPath: "cartApi",
    tagTypes: [cn.CART_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getCartByUserId: builder.query<Cart, number>({
            query: (userId) => ({
                params: { userId },
                url: cartResource,
            }),
        }),
        saveCartItem: builder.mutation<Cart, tp.SaveCartItemArg>({
            query: (arg) => ({
                body: arg,
                method: "PATCH",
                url: cartResource,
            }),
        }),
    }),
});

export const {
    useGetCartByUserIdQuery,
    useSaveCartItemMutation,
} = cartApi;
