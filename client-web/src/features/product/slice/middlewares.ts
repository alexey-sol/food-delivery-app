import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "@/shared/utils/handlers";
import { productApi } from "../services/api";

const { getProductsByPlaceId } = productApi.endpoints;

export const productListener = createListenerMiddleware();

productListener.startListening({
    matcher: isAnyOf(
        getProductsByPlaceId.matchRejected,
    ),
    effect: handleFailureEffect,
});
