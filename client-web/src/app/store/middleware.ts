import { authApi } from "features/auth/services/api";
import { authListener } from "features/auth/slice/middlewares";
import { cartApi } from "features/cart/services/api";
import { cartListener } from "features/cart/slice/middlewares";
import { orderApi } from "features/order/services/api";
import { orderListener } from "features/order/slice/middlewares";
import { placeApi } from "features/place/services/api";
import { placeListener } from "features/place/slice/middlewares";
import { productApi } from "features/product/services/api";
import { productListener } from "features/product/slice/middlewares";

export const listenerMiddleware = [
    authListener.middleware,
    cartListener.middleware,
    orderListener.middleware,
    placeListener.middleware,
    productListener.middleware,
];

export const apiMiddleware = [
    authApi.middleware,
    cartApi.middleware,
    orderApi.middleware,
    placeApi.middleware,
    productApi.middleware,
];
