import type { Page } from "shared/models";
import type { HasId } from "shared/models/props";

export type StoreAddress = {
    addressLine: string;
    city: string;
};

export type StorePreview = HasId & {
    address: StoreAddress;
    createdAt: string;
    description?: string;
    name: string;
    updatedAt: string;
};

export type StorePage = Page<StorePreview[]>;
