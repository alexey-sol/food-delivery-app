import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "@/shared/utils/handlers";
import { cartApi } from "../services/api";

const { getCartsByUserId, saveCartItem } = cartApi.endpoints;

export const cartListener = createListenerMiddleware();

cartListener.startListening({
    matcher: isAnyOf(
        getCartsByUserId.matchRejected,
        saveCartItem.matchRejected,
    ),
    effect: handleFailureEffect,
});
