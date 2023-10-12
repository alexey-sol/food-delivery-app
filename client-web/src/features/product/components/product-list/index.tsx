import React, { useMemo, type FC } from "react";
import { Box, type SxProps, type Theme } from "@mui/material";
import { useProductPageContext } from "@/features/product/contexts/product-page";
import { DEFAULT_ITEM_SX } from "@/shared/components/layout";
import { Product } from "../product";

export type ProductListProps = {
    itemSx?: SxProps<Theme>;
};

export const ProductList: FC<ProductListProps> = ({ itemSx = DEFAULT_ITEM_SX }) => {
    const { products } = useProductPageContext();

    const resultItemSx = useMemo(() => ({
        ...DEFAULT_ITEM_SX,
        ...itemSx,
    }), [itemSx]);

    const productOverviews = products.map((product) => (
        <Box key={product.id} sx={resultItemSx}>
            <Product product={product} />
        </Box>
    ));

    return (
        <Box>
            {productOverviews}
        </Box>

    );
};
