import { configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";

import { cartApi } from "features/user/services/api/cart-api";
import { productApi } from "features/product/services/api";
import { storeApi } from "features/store/services/api";

import { rootReducer } from "./reducer";

export const store = configureStore({
    devTools: process.env.NODE_ENV !== "production",
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .concat(cartApi.middleware)
        .concat(productApi.middleware)
        .concat(storeApi.middleware),
} as const);

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
