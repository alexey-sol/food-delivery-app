import React, { type FC } from "react";
import {
    AppBar, Avatar, Box, Button, Container, IconButton, Toolbar, Tooltip, Typography,
} from "@mui/material";
import { appConfig } from "app/app-config";
import { AppLink } from "shared/components/app-link";
import { url } from "shared/const";

const pages = [
    {
        title: "Stores",
        path: url.STORE,
    },
] as const;

const settings = ["Profile", "Account", "Dashboard", "Logout"];

export const Header: FC = () => {
    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        // TODO
    };

    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <AppLink to="/">
                        <Typography
                            variant="h6"
                            noWrap
                            sx={{
                                mr: 2,
                                display: { xs: "none", md: "flex" },
                                fontFamily: "monospace",
                                fontWeight: 700,
                                letterSpacing: ".3rem",
                                color: "inherit",
                            }}
                        >
                            {appConfig.appName}
                        </Typography>
                    </AppLink>

                    <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
                        {pages.map(({ path, title }) => (
                            <AppLink key={path} to={path}>
                                <Button sx={{ color: "white", display: "block" }}>
                                    {title}
                                </Button>
                            </AppLink>
                        ))}
                    </Box>

                    <Box sx={{ flexGrow: 0 }}>
                        <Tooltip title="Open settings">
                            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                <Avatar alt="user-name" />
                            </IconButton>
                        </Tooltip>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    );
};
