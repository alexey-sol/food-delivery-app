import type { SnackbarArg } from "../models";

type CreateSnackbarArg = (message: SnackbarArg["message"]) => SnackbarArg;

export const createSuccessSnackbarArg: CreateSnackbarArg = (message) => ({
    message,
    view: "success",
});

export const createFailureSnackbarArg: CreateSnackbarArg = (message) => ({
    message,
    view: "error",
});

export const createWarningSnackbarArg: CreateSnackbarArg = (message) => ({
    message,
    view: "warning",
});
