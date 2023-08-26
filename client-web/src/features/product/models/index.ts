import type { Page } from "shared/models";
import type { HasId } from "shared/models/props";

export type ProductPreview = HasId & {
    createdAt: string;
    description?: string;
    name: string;
    updatedAt: string;
};

export type ProductPage = Page<ProductPreview[]>;
