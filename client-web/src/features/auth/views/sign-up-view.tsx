import React, { memo, type FC } from "react";
import { Box } from "@mui/material";

import { SignUpForm } from "../components/sign-up-form";

export const SignUpView: FC = () => (
    <Box
        sx={{
            display: "flex", justifyContent: "center",
        }}
    >
        <SignUpForm />
    </Box>
);

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <SignUpView />
));
