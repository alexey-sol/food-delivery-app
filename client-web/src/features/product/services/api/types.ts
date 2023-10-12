import type { PagingOptions } from "@/shared/models";

export type GetProductsByPlaceIdArg = {
    paging: Pick<PagingOptions, "page" | "size">;
    placeId?: number;
};
