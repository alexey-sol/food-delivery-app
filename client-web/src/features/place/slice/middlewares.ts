import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "shared/utils/handlers";
import { placeApi } from "../services/api";

const { getPlacesByLocalityId } = placeApi.endpoints;

export const placeListener = createListenerMiddleware();

placeListener.startListening({
    matcher: isAnyOf(
        getPlacesByLocalityId.matchRejected,
    ),
    effect: handleFailureEffect,
});
