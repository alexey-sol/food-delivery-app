import { type PagingOptions } from "@/shared/models";

type Paging = Pick<PagingOptions, "page" | "size">;

const PAGE_OFFSET = 1;

const transformPaging = (paging?: Paging): Paging | undefined => {
    if (paging) {
        return {
            ...paging,
            page: paging.page - PAGE_OFFSET,
        };
    }

    return undefined;
};

type TransformArgArg<T> = {
    paging: Paging;
} & T;

export const transformGetItemsArg = <T>(
    { paging, ...rest }: TransformArgArg<T>,
) => ({
        ...transformPaging(paging),
        ...rest,
    });
