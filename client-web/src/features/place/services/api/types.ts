import type { PagingOptions } from "shared/models";

export type GetPlacesByIdArg = {
    cityId?: number;
    paging: Pick<PagingOptions, "page" | "size">;
};
