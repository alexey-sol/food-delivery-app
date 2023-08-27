import type { ProductPreview } from "features/product/models";

export type Cart = {
    id: number;
    totalPrice: number;
    items: ProductPreview[];
};
