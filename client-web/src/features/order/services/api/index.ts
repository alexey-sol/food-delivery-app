import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { type Order } from "@/features/order/models";
import { getApiPath } from "@/shared/utils/formatters/api-path";
import { resources, url } from "@/shared/const";

import type * as tp from "./types";

const baseUrl = getApiPath(url.API);

export const orderApi = createApi({
    reducerPath: "orderApi",
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getOrdersByUserId: builder.query<Order[], number | undefined>({
            query: (userId) => ({
                params: { userId },
                url: `${resources.USER}/${userId}/${resources.ORDER}`,
            }),
        }),
        createOrder: builder.mutation<Order, tp.CreateOrderArg>({
            query: (arg) => ({
                body: arg,
                method: "POST",
                url: `${resources.USER}/${arg.userId}/${resources.ORDER}`,
            }),
        }),
    }),
});

export const {
    useCreateOrderMutation,
    useLazyGetOrdersByUserIdQuery,
} = orderApi;
