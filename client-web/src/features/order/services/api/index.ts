import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";

import type { Order } from "features/order/models";
import { baseUrl } from "./utils";
import type * as tp from "./types";

const { orderResource } = appConfig;

export const orderApi = createApi({
    reducerPath: "orderApi",
    // tagTypes: [cn.ORDER_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getAllOrdersByUserId: builder.query<Order[], number | undefined>({ // TODO OR undefined? otherwise there's an issue when passing arg despite presence of skip token
            query: (userId) => ({
                params: { userId },
                url: orderResource,
            }),
        }),
        createOrder: builder.mutation<Order, tp.CreateOrderArg>({
            query: (arg) => ({
                body: arg,
                method: "POST",
                url: orderResource,
            }),
        }),
    }),
});

export const {
    useCreateOrderMutation,
    useLazyGetAllOrdersByUserIdQuery,
} = orderApi;
