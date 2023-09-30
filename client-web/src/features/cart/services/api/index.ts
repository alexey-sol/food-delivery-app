import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";
import type { Cart } from "features/cart/models";

import { baseUrl } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { cartResource, userResource } = appConfig;

export const cartApi = createApi({
    reducerPath: "cartApi",
    tagTypes: [cn.CART_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getCartsByUserId: builder.query<Cart[], number>({
            query: (userId) => ({
                params: { userId },
                url: `${userResource}/${userId}/${cartResource}`,
            }),
        }),
        saveCartItem: builder.mutation<Cart, tp.SaveCartItemArg>({
            query: (arg) => ({
                body: arg,
                method: "PATCH",
                url: `${userResource}/${arg.userId}/${cartResource}`,
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
