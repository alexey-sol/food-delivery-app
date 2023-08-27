import React, { memo, type FC } from "react";
import { Box } from "@mui/material";
import { SignInForm } from "../components/sign-in-form";

export const SignInView: FC = () => (
    <Box
        sx={{
            display: "flex", justifyContent: "center",
        }}
    >
        <SignInForm />
    </Box>
);

//

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <SignInView />
));
