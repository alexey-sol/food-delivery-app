import type { HasHttpStatus, HasId } from "./props";

export type Page<E = unknown> = {
    content: E;
    size: number;
    totalElements: number;
};

export type PagingOptions = Pick<Page, "size" | "totalElements"> & {
    page: number;
};

export type AddressPreview = HasId & {
    addressLine: string;
};

export type Address = AddressPreview & {
    city: HasId & {
        name: string;
    }
};

export type ApiError = HasHttpStatus & {
    detail: string;
    title: string;
};
