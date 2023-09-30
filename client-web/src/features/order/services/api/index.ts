import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";

import type { Order } from "features/order/models";
import { baseUrl } from "./utils";
import type * as tp from "./types";

const { orderResource, userResource } = appConfig;

export const orderApi = createApi({
    reducerPath: "orderApi",
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getOrdersByUserId: builder.query<Order[], number | undefined>({
            query: (userId) => ({
                params: { userId },
                url: `${userResource}/${userId}/${orderResource}`,
            }),
        }),
        createOrder: builder.mutation<Order, tp.CreateOrderArg>({
            query: (arg) => ({
                body: arg,
                method: "POST",
                url: `${userResource}/${arg.userId}/${orderResource}`,
            }),
        }),
    }),
});

export const {
    useCreateOrderMutation,
    useLazyGetOrdersByUserIdQuery,
} = orderApi;
