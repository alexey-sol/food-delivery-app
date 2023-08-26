import type { PagingOptions } from "shared/models";

export type GetStoresArg = {
    paging: Pick<PagingOptions, "page" | "size">;
};
