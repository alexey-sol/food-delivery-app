import {
    type AnyAction, type ListenerEffect, type ThunkDispatch, isRejectedWithValue,
} from "@reduxjs/toolkit";
import { createFailureSnackbarArg } from "@/features/feedback/slice/utils";
import { setSnackbar } from "@/features/feedback/slice";
import { isApiError } from "./helpers/predicates";

const DEFAULT_ERROR = "Something went wrong";

type AppListenerEffect = ListenerEffect<
AnyAction,
unknown,
ThunkDispatch<unknown, unknown, AnyAction>,
unknown
>;

export const handleFailureEffect: AppListenerEffect = (action, listenerApi) => {
    const error = action.payload?.data;

    if (isRejectedWithValue(action)) {
        const message = isApiError(error)
            ? error.detail
            : DEFAULT_ERROR;

        const arg = createFailureSnackbarArg(message);

        listenerApi.dispatch(setSnackbar(arg));
    }
};
