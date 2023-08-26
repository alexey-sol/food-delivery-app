import React, { useMemo, type FC } from "react";
import {
    Card, CardContent, Typography, type SxProps, type Theme,
} from "@mui/material";
import { useProductPageContext } from "features/product/contexts/product-page";
import { DEFAULT_ITEM_SX } from "shared/components/layout";

const NO_DESCRIPTION_TEXT = "No description";

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
        <Card key={product.id} sx={resultItemSx}>
            <CardContent>
                <Typography variant="h5" color="text.secondary" gutterBottom>
                    {product.name}
                </Typography>

                <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                    {product.description ?? NO_DESCRIPTION_TEXT}
                </Typography>
            </CardContent>
        </Card>
    ));

    return (
        <section>
            {productOverviews}
        </section>
    );
};
