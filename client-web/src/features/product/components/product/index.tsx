import React, { useCallback, type FC } from "react";
import {
    Card, CardContent, Typography, Button,
} from "@mui/material";
import { useCartContext } from "features/user/contexts/cart";
import type { ProductPreview } from "features/product/models";

const QUANTITY_UPDATE_STEP = 1;
const ADD_ITEM_TO_CART_TEXT = "+";
const REMOVE_ITEM_FROM_CART_TEXT = "-";
const NO_DESCRIPTION_TEXT = "No description";

export type ProductProps = {
    product: ProductPreview;
};

export const Product: FC<ProductProps> = ({ product }) => {
    const {
        description,
        id,
        name,
    } = product;

    const { cart: { saveCartItem } } = useCartContext();

    const addItemToCart = useCallback(() => {
        saveCartItem({
            productId: id,
            quantity: QUANTITY_UPDATE_STEP,
        });
    }, [id, saveCartItem]);

    const removeItemFromCart = useCallback(() => {
        saveCartItem({
            productId: id,
            quantity: -QUANTITY_UPDATE_STEP,
        });
    }, [id, saveCartItem]);

    return (
        <Card>
            <CardContent>
                <Typography variant="h5" color="text.secondary" gutterBottom>
                    {name}
                </Typography>

                <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                    {description ?? NO_DESCRIPTION_TEXT}
                </Typography>

                <Button onClick={addItemToCart}>{ADD_ITEM_TO_CART_TEXT}</Button>
                <Button onClick={removeItemFromCart}>{REMOVE_ITEM_FROM_CART_TEXT}</Button>
            </CardContent>
        </Card>
    );
};
