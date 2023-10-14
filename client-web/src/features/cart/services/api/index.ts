import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { type Cart } from "@/features/cart/models";
import { resources, url } from "@/shared/const";
import { getApiPath } from "@/shared/utils/formatters/api-path";

import * as cn from "./const";
import type * as tp from "./types";

const baseUrl = getApiPath(url.API);

export const cartApi = createApi({
    reducerPath: "cartApi",
    tagTypes: [cn.CART_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getCartsByUserId: builder.query<Cart[], number>({
            query: (userId) => ({
                params: { userId },
                url: `${resources.USER}/${userId}/${resources.CART}`,
            }),
        }),
        saveCartItem: builder.mutation<Cart, tp.SaveCartItemArg>({
            query: (arg) => ({
                body: arg,
                method: "PATCH",
                url: `${resources.USER}/${arg.userId}/${resources.CART}`,
            }),
            async onQueryStarted({ userId, placeId, ...patch }, { dispatch, queryFulfilled }) {
                try {
                    const { data: savedCart } = await queryFulfilled;

                    dispatch(
                        cartApi.util.updateQueryData("getCartsByUserId", userId, (draft) => {
                            const index = draft.findIndex((cart) => cart.place.id === placeId);

                            if (index >= 0) {
                                Object.assign(draft[index], savedCart);
                            } else {
                                draft.push(savedCart);
                            }
                        }),
                    );
                } catch (error) {
                    console.error(error);
                }
            },
        }),
    }),
});

export const {
    useLazyGetCartsByUserIdQuery,
    useSaveCartItemMutation,
} = cartApi;
