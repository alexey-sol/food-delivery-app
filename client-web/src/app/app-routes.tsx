import { useAuthContext } from "features/auth/contexts/auth";
import React, { lazy, type FC } from "react";
import {
    Navigate, Outlet, Route, Routes, useLocation,
} from "react-router-dom";
import { url } from "shared/const";

const CartView = lazy(() => import("features/cart/views/cart-view"));
const StoreListView = lazy(() => import("features/store/views/store-list-view"));
const OrderView = lazy(() => import("features/order/views/order-view"));
const ProductListView = lazy(() => import("features/product/views/product-list-view"));
const SignInView = lazy(() => import("features/auth/views/sign-in-view"));
const SignUpView = lazy(() => import("features/auth/views/sign-up-view"));

const GuestRoute = ({ children }: { children: any }) => { // TODO
    const auth = useAuthContext();
    const location = useLocation();

    if (auth.profile) {
        return <Navigate to="/" state={{ from: location }} />;
    }

    return children;
};

const AuthRoute = ({ children }: { children: any }) => { // TODO
    const auth = useAuthContext();
    const location = useLocation();

    if (!auth.profile) {
        return <Navigate to="/sign-in" state={{ from: location }} />;
    }

    return children;
};

export const AppRoutes: FC = () => (
    <Routes>
        <Route index element={<div>Home</div>} />

        <Route path={url.STORE}>
            <Route index element={<AuthRoute><StoreListView /></AuthRoute>} />

            <Route path=":storeId">
                <Route path={url.PRODUCT}>
                    <Route index element={<AuthRoute><ProductListView /></AuthRoute>} />
                </Route>
            </Route>
        </Route>

        <Route path={url.CART} element={<AuthRoute><CartView /></AuthRoute>} />

        <Route path={url.ORDER} element={<AuthRoute><OrderView /></AuthRoute>} />

        <Route path={url.SIGN_IN} element={<GuestRoute><SignInView /></GuestRoute>} />

        <Route path={url.SIGN_UP} element={<GuestRoute><SignUpView /></GuestRoute>} />

        <Route path="*" element={<div>404</div>} />
    </Routes>
);
