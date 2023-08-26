import { configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";

import { storeApi } from "features/store/services/api";

import { rootReducer } from "./reducer";

export const store = configureStore({
    devTools: process.env.NODE_ENV !== "production", // TODO isProduction
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .concat(storeApi.middleware),
} as const);

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
