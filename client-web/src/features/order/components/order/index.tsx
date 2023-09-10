import React, { useEffect, type FC } from "react";
import { useAuthContext } from "features/auth/contexts/auth";
import {
    Box, Chip, List, ListItem, Paper, Typography,
} from "@mui/material";
import { grey } from "@mui/material/colors";
import { useUserContext } from "features/user/contexts/user";
import { amountToRub } from "shared/utils/formatters/number";

const NOTHING_FOUND_TEXT = "Nothing found";
const DELIVERY_PRICE = 20000;

const statusTranslation: Record<string, string> = {
    CANCELLED: "Cancelled",
    COMPLETED: "Completed",
    DELIVERING: "Delivering",
    FAILED: "Failed",
    PROCESSING: "Processing",
};

export const Order: FC = () => {
    const { orders: { getOrders, orders } } = useUserContext();
    const { profile } = useAuthContext();

    if (!profile) {
        return null;
    }

    useEffect(() => {
        getOrders();
    }, [getOrders]);

    return (
        <Box sx={{ display: "flex", flexDirection: "column", rowGap: 1 }}>
            {orders.length === 0 && (
                <Box>
                    <Typography>
                        {NOTHING_FOUND_TEXT}
                    </Typography>
                </Box>
            )}

            {orders.map((order) => (
                <Paper key={order.id}>
                    <Box sx={{ px: 2, pt: 2 }}>
                        <Typography gutterBottom>{order.place.name}</Typography>
                        <Typography color={grey[500]}>{order.place.address.addressLine}</Typography>
                    </Box>

                    <List sx={{ my: 1 }} component="nav" aria-label="mailbox folders">
                        {order.orderItems?.map((item) => (
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
                                    {amountToRub(item.product.price * item.quantity)}
                                    {" "}
                                    rub
                                </Typography>
                            </ListItem>
                        ))}
                    </List>

                    <Box sx={{
                        px: 2, pb: 2, display: "flex", flexDirection: "column", rowGap: 1,
                    }}
                    >
                        <Chip sx={{ width: "fit-content", my: 2 }} label={`Total price: ${amountToRub(order.totalPrice + DELIVERY_PRICE)} rub`} />

                        <Typography color="secondary">
                            Status:
                            {" "}
                            {statusTranslation[order.status]}
                        </Typography>

                        <Typography color={grey[500]}>
                            Delivery address:
                            {" "}
                            {profile.address.addressLine}
                            .
                        </Typography>
                    </Box>
                </Paper>
            ))}
        </Box>
    );
};
