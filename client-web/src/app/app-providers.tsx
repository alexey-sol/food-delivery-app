import React, { type FC, StrictMode, Suspense } from "react";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { compose } from "@reduxjs/toolkit";
import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import { ErrorBoundary } from "shared/components/error-boundary";

import { store } from "./store";
import { theme } from "./style/theme";

type WithProvider = (component: FC<void>) => FC<void>;

export const withStore: WithProvider = (component) => () => (
    <Provider store={store}>
        {component()}
    </Provider>
);

export const withRouter: WithProvider = (component) => () => (
    <BrowserRouter>
        <Suspense fallback={<section>...</section>}>
            {component()}
        </Suspense>
    </BrowserRouter>
);

export const withTheme: WithProvider = (component) => () => (
    <ThemeProvider theme={theme}>
        <CssBaseline />
        {component()}
    </ThemeProvider>
);

export const withFallback: WithProvider = (component) => () => (
    <StrictMode>
        <ErrorBoundary>
            {component()}
        </ErrorBoundary>
    </StrictMode>
);

export const withProviders = compose<FC>(
    withStore,
    withRouter,
    withTheme,
    withFallback,
);
