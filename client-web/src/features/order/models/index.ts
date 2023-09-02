import type { ProductPreview } from "features/product/models";
import type { StorePreview } from "features/store/models";
import type { HasId } from "shared/models/props";

export type OrderItem = HasId & {
    createdAt: string;
    product: ProductPreview;
    quantity: number;
    updatedAt: string;
};

export type Order = HasId & {
    createdAt: string;
    orderItems: OrderItem[];
    status: string; // TODO enum
    store: StorePreview;
    totalPrice: number;
    updatedAt: string;
};
