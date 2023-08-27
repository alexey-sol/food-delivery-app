import React, { type FC } from "react";
import {
    AppBar, Avatar, Box, Button, Container, IconButton, Menu, MenuItem, Toolbar, Tooltip, Typography,
} from "@mui/material";
import { appConfig } from "app/app-config";
import { AppLink } from "shared/components/app-link";
import { url } from "shared/const";
import { useAuthContext } from "features/auth/contexts/auth";

type PageData = {
    path: string;
    title: string;
};

export const Header: FC = () => {
    const { profile, signOut } = useAuthContext();

    const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);

    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        // TODO
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
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
                        <AppLink to={`/${url.STORE}`}>
                            <Button sx={{ color: "white", display: "block" }}>
                                Stores
                            </Button>
                        </AppLink>

                        {!profile && (
                            <AppLink to={`/${url.SIGN_IN}`}>
                                <Button sx={{ color: "white", display: "block" }}>
                                    Sign In
                                </Button>
                            </AppLink>
                        )}
                    </Box>

                    <Box sx={{ flexGrow: 0 }}>
                        <Tooltip title={profile?.fullName}>
                            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                                <Avatar alt={profile?.fullName ?? "Profile"} />
                            </IconButton>
                        </Tooltip>
                        <Menu
                            sx={{ mt: "45px" }}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: "top",
                                horizontal: "right",
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: "top",
                                horizontal: "right",
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >

                            <MenuItem onClick={handleCloseUserMenu}>
                                <AppLink to={`/${url.CART}`}>
                                    <Typography textAlign="center">Carts</Typography>
                                </AppLink>
                            </MenuItem>

                            {profile && (

                                <MenuItem onClick={handleCloseUserMenu}>
                                    <AppLink to={`/${url.ORDER}`}>
                                        <Typography textAlign="center">Orders</Typography>
                                    </AppLink>
                                </MenuItem>
                            )}

                            {profile && (
                                <MenuItem onClick={() => {
                                    signOut();
                                    handleCloseUserMenu();
                                }}
                                >
                                    <Typography textAlign="center">Sign Out</Typography>
                                </MenuItem>

                            )}

                        </Menu>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    );
};
