import { configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";

import { rootReducer } from "./reducer";
import { apiMiddleware, listenerMiddleware } from "./middleware";

export const store = configureStore({
    devTools: process.env.NODE_ENV !== "production",
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .prepend(listenerMiddleware)
        .concat(apiMiddleware),
} as const);

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
