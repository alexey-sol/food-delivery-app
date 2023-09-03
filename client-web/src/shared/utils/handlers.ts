import type { AnyAction, ListenerEffect, ThunkDispatch } from "@reduxjs/toolkit";
import { createFailureSnackbarArg } from "features/feedback/slice/utils";
import { setSnackbar } from "features/feedback/slice";
import { isApiError } from "./helpers/predicates";

type AppListenerEffect = ListenerEffect<
AnyAction,
unknown,
ThunkDispatch<unknown, unknown, AnyAction>,
unknown
>;

export const handleFailureEffect: AppListenerEffect = (action, listenerApi) => {
    const error = action.payload?.data;

    if (isApiError(error)) {
        const arg = createFailureSnackbarArg(error.detail);
        listenerApi.dispatch(setSnackbar(arg));
    }
};
