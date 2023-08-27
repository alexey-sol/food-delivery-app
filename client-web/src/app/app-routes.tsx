import React, { lazy, type FC } from "react";
import { Route, Routes } from "react-router-dom";
import { url } from "shared/const";

const CartView = lazy(() => import("features/cart/views/cart-view"));
const StoreListView = lazy(() => import("features/store/views/store-list-view"));
const ProductListView = lazy(() => import("features/product/views/product-list-view"));
const SignInView = lazy(() => import("features/auth/views/sign-in-view"));
const SignUpView = lazy(() => import("features/auth/views/sign-up-view"));

export const AppRoutes: FC = () => (
    <Routes>
        <Route index element={<div>Home</div>} />

        <Route path={url.STORE}>
            <Route index element={<StoreListView />} />

            <Route path=":storeId">
                <Route path={url.PRODUCT}>
                    <Route index element={<ProductListView />} />
                </Route>
            </Route>
        </Route>

        <Route path={url.CART} element={<CartView />} />

        <Route path={url.SIGN_IN} element={<SignInView />} />

        <Route path={url.SIGN_UP} element={<SignUpView />} />

        <Route path="*" element={<div>404</div>} />
    </Routes>
);
