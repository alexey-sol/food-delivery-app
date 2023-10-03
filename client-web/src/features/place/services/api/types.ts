import type { PagingOptions } from "shared/models";

export type GetPlacesByIdArg = {
    localityId?: number;
    paging: Pick<PagingOptions, "page" | "size">;
};
