import React, { useEffect, type FC, useMemo } from "react";
import {
    Accordion, AccordionDetails, AccordionSummary, Box, Divider, List, ListItem, ListItemText, Tab, Tabs, Typography,
} from "@mui/material";
import { grey } from "@mui/material/colors";
import { useUserContext } from "features/user/contexts/user";

const CartItemList = () => {

};

export const Cart: FC = () => {
    const { carts } = useUserContext();

    useEffect(() => {
        carts.getCarts();
    }, [carts.getCarts]);

    console.log(carts);

    const [value, setValue] = React.useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ display: "flex", flexDirection: "column", rowGap: "1rem" }}>
            <Typography variant="h5">Carts</Typography>

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
                                <ListItem divider sx={{ display: "flex", justifyContent: "space-between" }}>
                                    <Box sx={{ display: "flex" }}>
                                        <Typography color={grey[600]}>
                                            {item.product.name}
                                        </Typography>

                                        {item.quantity > 1 && (
                                            <Typography color={grey[600]} sx={{ mx: 1}}>
                                                {`x${item.quantity}`}
                                            </Typography>
                                        )}
                                    </Box>

                                    <Typography>
                                        {(item.product.price * item.quantity) / 100} rub
                                    </Typography>
                                </ListItem>
                            ))}
                        </List>
                    </Box>
                ) : null))}
        </Box>
    );
};
