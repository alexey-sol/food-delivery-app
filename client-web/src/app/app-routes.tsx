import React, { lazy, type FC } from "react";
import { Route, Routes } from "react-router-dom";
import { url } from "shared/const";

const StoreListView = lazy(() => import("features/store/views/store-list-view"));
const ProductListView = lazy(() => import("features/product/views/product-list-view"));

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

        <Route path="*" element={<div>404</div>} />
    </Routes>
);
