import { combineReducers } from "@reduxjs/toolkit";

import { cartApi } from "features/user/services/api/cart-api";
import { productApi } from "features/product/services/api";
import { storeApi } from "features/store/services/api";
import { productReducer } from "features/product/slice";
import { storeReducer } from "features/store/slice";

export const rootReducer = combineReducers({
    [cartApi.reducerPath]: cartApi.reducer,
    [productApi.reducerPath]: productApi.reducer,
    [storeApi.reducerPath]: storeApi.reducer,
    product: productReducer,
    store: storeReducer,
});
