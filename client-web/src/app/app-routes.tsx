import React, { lazy, type FC } from "react";
import {
    Navigate, Route, Routes, useLocation,
} from "react-router-dom";

import { useAuthContext } from "@/features/auth/contexts/auth";
import { url } from "@/shared/const";

const CartView = lazy(() => import("@/features/cart/views/cart-view"));
const OrderView = lazy(() => import("@/features/order/views/order-view"));
const PlaceListView = lazy(() => import("@/features/place/views/place-list-view"));
const ProductListView = lazy(() => import("@/features/product/views/product-list-view"));
const SignInView = lazy(() => import("@/features/auth/views/sign-in-view"));
const SignUpView = lazy(() => import("@/features/auth/views/sign-up-view"));

const RootRoute = () => <Navigate to={url.PLACE} />;

const GuestRoute = ({ children }: { children: any }) => {
    const auth = useAuthContext();
    const location = useLocation();

    if (auth.profile) {
        return <Navigate to="/" state={{ from: location }} />;
    }

    return children;
};

const AuthRoute = ({ children }: { children: any }) => {
    const auth = useAuthContext();
    const location = useLocation();

    if (!auth.profile) {
        return <Navigate to="/sign-in" state={{ from: location }} />;
    }

    return children;
};

export const AppRoutes: FC = () => (
    <Routes>
        <Route index element={<RootRoute />} />

        <Route path={url.PLACE}>
            <Route index element={<AuthRoute><PlaceListView /></AuthRoute>} />

            <Route path=":placeId">
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
