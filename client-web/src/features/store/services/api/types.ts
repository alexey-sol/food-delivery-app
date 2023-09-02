import type { PagingOptions } from "shared/models";

export type GetStoresArg = {
    cityId?: number;
    paging: Pick<PagingOptions, "page" | "size">;
};
