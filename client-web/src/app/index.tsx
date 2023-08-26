import React, { type FC } from "react";

import { AppLayout } from "shared/components/layout";

import { withProviders } from "./app-providers";
import { AppRoutes } from "./app-routes";

export const App: FC = () => (
    <AppLayout>
        <AppRoutes />
    </AppLayout>
);

export const AppWithProviders = withProviders(App);
