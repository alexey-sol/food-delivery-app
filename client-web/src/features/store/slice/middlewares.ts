import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "shared/utils/handlers";
import { storeApi } from "../services/api";

const { getStores } = storeApi.endpoints;

export const storeListener = createListenerMiddleware();

storeListener.startListening({
    matcher: isAnyOf(
        getStores.matchRejected,
    ),
    effect: handleFailureEffect,
});
