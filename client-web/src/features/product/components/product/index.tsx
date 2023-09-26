import React, { useCallback, type FC } from "react";
import {
    Card, CardContent, Typography, Button, Box, CircularProgress,
} from "@mui/material";
import { useUserContext } from "features/user/contexts/user";
import { amountToRub } from "shared/utils/formatters/number";
import type { ProductPreview } from "features/product/models";
import { usePlacePageContext } from "features/place/contexts/place-page";

const COUNT_UPDATE_STEP = 1;
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
        price,
    } = product;

    const { currentPlace: place } = usePlacePageContext()
    const { carts: { getCartByPlaceId, isPendingFor, saveCartItem } } = useUserContext();

    if (!place) {
        return null;
    }

    const cart = getCartByPlaceId(place.id); // TODO no product.place now
    const cartItem = cart?.cartItems.find((item) => item.product.id === id);

    const addItemToCart = useCallback(() => {
        saveCartItem({
            count: COUNT_UPDATE_STEP,
            operation: "ADD",
            productId: id,
            placeId: place.id,
        });
    }, [id, saveCartItem, place.id]);

    const removeItemFromCart = useCallback(() => {
        saveCartItem({
            count: COUNT_UPDATE_STEP,
            operation: "REMOVE",
            productId: id,
            placeId: place.id,
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

                <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                    {`Item price: ${amountToRub(price)} rub`}
                </Typography>

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
