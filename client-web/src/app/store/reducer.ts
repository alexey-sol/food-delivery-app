import { combineReducers } from "@reduxjs/toolkit";

import { authApi } from "features/auth/services/api";
import { cartApi } from "features/cart/services/api";
import { orderApi } from "features/order/services/api";
import { productApi } from "features/product/services/api";
import { storeApi } from "features/store/services/api";
import { userApi } from "features/user/services/api";
import { productReducer } from "features/product/slice";
import { storeReducer } from "features/store/slice";

export const rootReducer = combineReducers({
    [authApi.reducerPath]: authApi.reducer,
    [cartApi.reducerPath]: cartApi.reducer,
    [orderApi.reducerPath]: orderApi.reducer,
    [productApi.reducerPath]: productApi.reducer,
    [storeApi.reducerPath]: storeApi.reducer,
    [userApi.reducerPath]: userApi.reducer,
    product: productReducer,
    store: storeReducer,
});
