import React, { useEffect, type FC, useCallback } from "react";
import {
    Box, Button, Chip, List, ListItem, Tab, Tabs, Typography,
} from "@mui/material";
import { grey } from "@mui/material/colors";
import { useAuthContext } from "features/auth/contexts/auth";
import { useUserContext } from "features/user/contexts/user";
import { useOrderContext } from "features/order/contexts/order";
import { useNavigate } from "react-router-dom";
import { url } from "shared/const";

const NOTHING_FOUND_TEXT = "Nothing found";
const DELIVERY_PRICE = 20000; // TODO hardcoded

export const Cart: FC = () => {
    const navigate = useNavigate();

    const { profile } = useAuthContext();
    const { orders } = useOrderContext();
    const { carts } = useUserContext();

    useEffect(() => {
        carts.getCarts();
    }, [carts.getCarts]);

    const [value, setValue] = React.useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    if (!profile) {
        return null;
    }

    return (
        <Box sx={{ display: "flex", flexDirection: "column", rowGap: "1rem" }}>
            <Typography variant="h5">Carts</Typography>

            {carts.carts.length === 0 && (
                <Box>
                    <Typography>
                        {NOTHING_FOUND_TEXT}
                    </Typography>
                </Box>
            )}

            {carts.carts.length > 0 && (
                <>
                    <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                        {carts.carts.map((cart) => (
                            <Tab key={cart.id} label={cart.store.name} />
                        ))}
                    </Tabs>

                    {carts.carts.map((cart, index) =>
                        (value === index ? (
                            <Box key={cart.id} sx={{ py: 1 }}>
                                <Typography color={grey[500]}>{cart.store.address.addressLine}</Typography>

                                <List sx={{ my: 1 }} component="nav" aria-label="mailbox folders">
                                    {cart.cartItems?.map((item) => (
                                        <ListItem key={item.id} divider sx={{ display: "flex", justifyContent: "space-between" }}>
                                            <Box sx={{ display: "flex" }}>
                                                <Typography>
                                                    {item.product.name}
                                                </Typography>

                                                {item.quantity > 1 && (
                                                    <Typography sx={{ mx: 1 }}>
                                                        {`x${item.quantity}`}
                                                    </Typography>
                                                )}
                                            </Box>

                                            <Typography>
                                                {(item.product.price * item.quantity) / 100}
                                                {" "}
                                                rub
                                            </Typography>
                                        </ListItem>
                                    ))}
                                </List>

                                <Box sx={{ my: 2, display: "flex", justifyContent: "space-between" }}>
                                    <Chip label={`Total price: ${(cart.totalPrice + DELIVERY_PRICE) / 100} rub`} variant="outlined" />
                                    <Chip label={`Delivery price: ${DELIVERY_PRICE / 100} rub`} />
                                </Box>

                                <Typography color={grey[500]} sx={{ my: 2 }}>
                                    Delivery address:
                                    {" "}
                                    {profile.address.addressLine}
                                    . Paying upon delivery, with cash or POS terminal.
                                </Typography>

                                {/* <AppLink to={`/${url.ORDER}`}> */}
                                <Button
                                    variant="contained"
                                    onClick={() => {
                                        orders.createOrder({
                                            orderItems: cart.cartItems.map(({ product, quantity }) => ({
                                                // productId: product.id,
                                                productId: product.id,
                                                quantity,
                                            })),
                                            storeId: cart.store.id,
                                        });

                                        // TODO what if error?

                                        // navigate(`/${url.ORDER}`);
                                    }}
                                >
                                    Order

                                </Button>
                                {/* </AppLink> */}
                            </Box>
                        ) : null))}
                </>
            )}

        </Box>
    );
};
