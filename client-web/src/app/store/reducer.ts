import { combineReducers } from "@reduxjs/toolkit";

import { authApi } from "@/features/auth/services/api";
import { cartApi } from "@/features/cart/services/api";
import { feedbackReducer } from "@/features/feedback/slice";
import { orderApi } from "@/features/order/services/api";
import { placeApi } from "@/features/place/services/api";
import { placeReducer } from "@/features/place/slice";
import { productApi } from "@/features/product/services/api";
import { productReducer } from "@/features/product/slice";

export const rootReducer = combineReducers({
    [authApi.reducerPath]: authApi.reducer,
    [cartApi.reducerPath]: cartApi.reducer,
    [orderApi.reducerPath]: orderApi.reducer,
    [placeApi.reducerPath]: placeApi.reducer,
    [productApi.reducerPath]: productApi.reducer,
    feedback: feedbackReducer,
    place: placeReducer,
    product: productReducer,
});
