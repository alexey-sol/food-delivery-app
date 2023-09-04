import type { Address, Page } from "shared/models";
import type { HasId } from "shared/models/props";

export type PlacePreview = HasId & {
    address: Address;
    createdAt: string;
    description?: string;
    name: string;
    updatedAt: string;
};

export type PlacePage = Page<PlacePreview[]>;
