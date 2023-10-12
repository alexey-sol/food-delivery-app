import type { AddressPreview, Page } from "@/shared/models";
import type { HasId } from "@/shared/models/props";

export type PlacePreview = HasId & {
    address: AddressPreview;
    createdAt: string;
    description?: string;
    name: string;
    updatedAt: string;
};

export type PlacePage = Page<PlacePreview[]>;
