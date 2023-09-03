import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "shared/utils/handlers";
import { productApi } from "../services/api";

const { getProducts } = productApi.endpoints;

export const productListener = createListenerMiddleware();

productListener.startListening({
    matcher: isAnyOf(
        getProducts.matchRejected,
    ),
    effect: handleFailureEffect,
});
