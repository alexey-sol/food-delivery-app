import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";

import { handleFailureEffect } from "@/shared/utils/handlers";

import { orderApi } from "../services/api";

const { createOrder, getOrdersByUserId } = orderApi.endpoints;

export const orderListener = createListenerMiddleware();

orderListener.startListening({
    matcher: isAnyOf(
        createOrder.matchRejected,
        getOrdersByUserId.matchRejected,
    ),
    effect: handleFailureEffect,
});
