import type { ProductPreview } from "features/product/models";
import type { PlacePreview } from "features/place/models";
import type { HasId } from "shared/models/props";

export type CartItem = HasId & {
    product: ProductPreview;
    quantity: number;
};

export type Cart = HasId & {
    cartItems: CartItem[];
    place: PlacePreview;
    totalPrice: number;
};
