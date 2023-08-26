import StoreListView from "features/store/views/store-list-view";
import React, { type FC } from "react";
import { Route, Routes } from "react-router-dom";
import { url } from "shared/const";

export const AppRoutes: FC = () => (
    <Routes>
        <Route index element={<div>Home</div>} />
        <Route path={url.STORE}>
            <Route index element={<StoreListView />} />
        </Route>
        <Route path="*" element={<div>404</div>} />
    </Routes>
);
