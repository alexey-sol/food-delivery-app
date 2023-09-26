import type { Page } from "shared/models";
import type { HasId } from "shared/models/props";

export type ProductPreview = HasId & {
    calories?: number;
    createdAt: string;
    description?: string;
    name: string;
    price: number;
    updatedAt: string;
};

export type ProductPage = Page<ProductPreview[]>;
