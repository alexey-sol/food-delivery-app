import React, { useCallback, type FC } from "react";
import {
    Card, CardContent, Typography, Button, Box, CircularProgress,
} from "@mui/material";
import { useUserContext } from "features/user/contexts/user";
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
        calories,
        description,
        id,
        name,
        place,
    } = product;

    const { carts: { getCartByPlaceId, isPendingFor, saveCartItem } } = useUserContext();

    const cart = getCartByPlaceId(place.id);
    const cartItem = cart?.cartItems.find((item) => item.product.id === id);

    const addItemToCart = useCallback(() => {
        saveCartItem({
            productId: id,
            quantity: QUANTITY_UPDATE_STEP,
            placeId: place.id,
        });
    }, [id, saveCartItem, place.id]);

    const removeItemFromCart = useCallback(() => {
        saveCartItem({
            productId: id,
            quantity: -QUANTITY_UPDATE_STEP,
            placeId: place.id,
            // TODO + cartId
        });
    }, [id, saveCartItem, place.id]);

    const isPending = isPendingFor(id);

    return (
        <Card>
            <CardContent sx={{ display: "flex", flexDirection: "column", rowGap: 0.5 }}>
                <Typography variant="h5" color="text.secondary" gutterBottom>
                    {name}
                </Typography>

                <Typography sx={{ fontSize: 14 }} component="section">
                    {description ?? NO_DESCRIPTION_TEXT}
                </Typography>

                {calories && (
                    <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                        {`Calories: ${calories}`}
                    </Typography>
                )}

                <Box sx={{ display: "flex", alignItems: "center", height: "40px" }}>
                    <Button disabled={isPending} onClick={removeItemFromCart}>{REMOVE_ITEM_FROM_CART_TEXT}</Button>

                    <Typography sx={{ minWidth: "1.5rem", textAlign: "center" }}>
                        {/* { !isPending ? (cartItem?.quantity ?? 0) : "  "} */}
                        {cartItem?.quantity ?? 0}
                    </Typography>

                    <Button disabled={isPending} onClick={addItemToCart}>{ADD_ITEM_TO_CART_TEXT}</Button>

                    {isPending && (
                        <CircularProgress />
                    )}
                </Box>
            </CardContent>
        </Card>
    );
};
