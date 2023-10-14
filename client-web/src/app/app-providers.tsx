import React, { type FC, StrictMode, Suspense } from "react";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { compose } from "@reduxjs/toolkit";
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import { GlobalStyles } from "@mui/material";

import { ErrorBoundary } from "@/shared/components/error-boundary";
import { AuthProvider } from "@/features/auth/contexts/auth";
import { UserProvider } from "@/features/user/contexts/user";

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
        <GlobalStyles
            styles={{
                body: { overflowY: "scroll" },
            }}
        />
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

export const withUser: WithProvider = (component) => () => (
    <AuthProvider>
        <UserProvider>
            {component()}
        </UserProvider>
    </AuthProvider>
);

export const withProviders = compose<FC>(
    withStore,
    withRouter,
    withTheme,
    withFallback,
    withUser,
);
