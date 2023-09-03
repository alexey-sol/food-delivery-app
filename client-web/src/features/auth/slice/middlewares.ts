import { createListenerMiddleware, isAnyOf } from "@reduxjs/toolkit";
import { handleFailureEffect } from "shared/utils/handlers";
import { authApi } from "../services/api";

const { signIn, signOut, signUp } = authApi.endpoints;

export const authListener = createListenerMiddleware();

authListener.startListening({
    matcher: isAnyOf(
        signIn.matchRejected,
        signOut.matchRejected,
        signUp.matchRejected,
    ),
    effect: handleFailureEffect,
});
