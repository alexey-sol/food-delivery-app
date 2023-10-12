import type { ProductPreview } from "@/features/product/models";
import type { PlacePreview } from "@/features/place/models";
import type { HasId } from "@/shared/models/props";

export type OrderItem = HasId & {
    createdAt: string;
    product: ProductPreview;
    quantity: number;
    updatedAt: string;
};

export type Order = HasId & {
    createdAt: string;
    orderItems: OrderItem[];
    place: PlacePreview;
    status: string;
    totalPrice: number;
    updatedAt: string;
};
