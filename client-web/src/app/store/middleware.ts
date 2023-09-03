import { authApi } from "features/auth/services/api";
import { authListener } from "features/auth/slice/middlewares";
import { cartApi } from "features/cart/services/api";
import { cartListener } from "features/cart/slice/middlewares";
import { orderApi } from "features/order/services/api";
import { orderListener } from "features/order/slice/middlewares";
import { productApi } from "features/product/services/api";
import { productListener } from "features/product/slice/middlewares";
import { storeApi } from "features/store/services/api";
import { storeListener } from "features/store/slice/middlewares";

export const listenerMiddleware = [
    authListener.middleware,
    cartListener.middleware,
    orderListener.middleware,
    productListener.middleware,
    storeListener.middleware,
];

export const apiMiddleware = [
    authApi.middleware,
    cartApi.middleware,
    orderApi.middleware,
    productApi.middleware,
    storeApi.middleware,
];
