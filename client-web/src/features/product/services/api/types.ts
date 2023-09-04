import type { PagingOptions } from "shared/models";

export type GetProductsArg = {
    paging: Pick<PagingOptions, "page" | "size">;
    placeId?: number;
};
