import React, { type FC } from "react";
import { Box } from "@mui/material";
import { useCartContext } from "features/user/contexts/cart";

export const Cart: FC = () => {
    const { cart } = useCartContext();

    return (
        <Box>
            cart!
        </Box>
    );
};
