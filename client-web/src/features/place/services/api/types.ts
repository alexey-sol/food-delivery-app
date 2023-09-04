import type { PagingOptions } from "shared/models";

export type GetPlacesArg = {
    cityId?: number;
    paging: Pick<PagingOptions, "page" | "size">;
};
