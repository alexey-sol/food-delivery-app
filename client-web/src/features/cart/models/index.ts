import type { ProductPreview } from "features/product/models";
import type { StorePreview } from "features/store/models";
import type { HasId } from "shared/models/props";

export type CartItem = HasId & {
    product: ProductPreview;
    quantity: number;
};

export type Cart = HasId & {
    cartItems: CartItem[];
    store: StorePreview;
    totalPrice: number;
};
