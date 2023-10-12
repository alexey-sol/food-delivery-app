import type { Cart } from "@/features/cart/models";
import type { Address } from "@/shared/models";
import type { HasId } from "@/shared/models/props";

export type User = HasId & {
    address: Address;
    carts: Cart[];
    fullName: string;
    phone: string;
};
