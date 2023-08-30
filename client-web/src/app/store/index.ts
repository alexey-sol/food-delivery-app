import { configureStore } from "@reduxjs/toolkit";
import { setupListeners } from "@reduxjs/toolkit/query";

import { authApi } from "features/auth/services/api";
import { cartApi } from "features/cart/services/api";
import { orderApi } from "features/order/services/api";
import { productApi } from "features/product/services/api";
import { storeApi } from "features/store/services/api";
import { userApi } from "features/user/services/api";

import { rootReducer } from "./reducer";

export const store = configureStore({
    devTools: process.env.NODE_ENV !== "production",
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware()
        .concat(authApi.middleware)
        .concat(cartApi.middleware)
        .concat(orderApi.middleware)
        .concat(productApi.middleware)
        .concat(storeApi.middleware)
        .concat(userApi.middleware),
} as const);

setupListeners(store.dispatch);

export type RootState = ReturnType<typeof store.getState>;

export type AppDispatch = typeof store.dispatch;
