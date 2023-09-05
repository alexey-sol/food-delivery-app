import React, { useMemo, type FC } from "react";
import { Box, Typography, type SxProps, type Theme } from "@mui/material";
import { useProductPageContext } from "features/product/contexts/product-page";
import { DEFAULT_ITEM_SX } from "shared/components/layout";
import { Product } from "../product";
import type { PlacePreview } from "features/place/models";

export type ProductListProps = {
    itemSx?: SxProps<Theme>;
    place?: PlacePreview;
};

export const ProductList: FC<ProductListProps> = ({ itemSx = DEFAULT_ITEM_SX, place }) => {


    const { products } = useProductPageContext();


    const resultItemSx = useMemo(() => ({
        ...DEFAULT_ITEM_SX,
        ...itemSx,
    }), [itemSx]);

    // const place = useMemo(() => getPlaceById(), [getPlaceById]);

    const productOverviews = products.map((product) => (
        <Box key={product.id} sx={resultItemSx}>
            <Product product={product} />
        </Box>
    ));

    return (
        <Box>
            {place && (
                <Typography gutterBottom variant="h5">{place.name}</Typography>
            )}

            <Box>
                {productOverviews}
            </Box>
        </Box>

    );
};
