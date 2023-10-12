import React, { useCallback, type FC, type PropsWithChildren } from "react";
import {
    Alert, Box, Container, Snackbar,
} from "@mui/material";
import { SnackbarArg } from "@/features/feedback/models";
import { useAppDispatch, useAppSelector } from "@/app/store/hooks";
import { resetSnackbar } from "@/features/feedback/slice";
import { selectSnackbar } from "@/features/feedback/slice/selectors";
import { Header } from "./header";

export type LayoutData = {
    snackbar?: SnackbarArg;
    resetSnackbar: () => void;
};

export const useLayoutData = (): LayoutData => {
    const dispatch = useAppDispatch();
    const onResetSnackbar = useCallback(() => {
        dispatch(resetSnackbar());
    }, [dispatch]);

    const snackbar = useAppSelector(selectSnackbar);

    return {
        snackbar,
        resetSnackbar: onResetSnackbar,
    };
};

export const AppLayout: FC<PropsWithChildren> = ({ children }) => {
    const { snackbar, resetSnackbar } = useLayoutData();

    return (
        <Container
            maxWidth="lg"
            sx={{
                display: "flex",
                flexDirection: "column",
                height: "100vh",
            }}
        >
            <Header />

            <Box sx={{ py: 2, flex: 1 }}>
                {children}
            </Box>

            <Snackbar
                anchorOrigin={{ vertical: "top", horizontal: "right" }}
                autoHideDuration={5000}
                onClose={resetSnackbar}
                open={!!snackbar}
            >
                {snackbar?.view && <Alert severity={snackbar?.view}>{snackbar?.message}</Alert>}
            </Snackbar>
        </Container>
    );
};
