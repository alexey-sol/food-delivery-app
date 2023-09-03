import type { HasHttpStatus } from "./props";

export type Page<E = unknown> = {
    content: E;
    size: number;
    totalElements: number;
};

export type PagingOptions = Pick<Page, "size" | "totalElements"> & {
    page: number;
};

export type Address = {
    id: number;
    addressLine: string;
    city: {
        id: number;
        name: string;
    }
};

export type ApiError = HasHttpStatus & {
    detail: string;
    title: string;
};
