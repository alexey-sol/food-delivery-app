import React, {
    useMemo, type PropsWithChildren, memo,
} from "react";
import {
    Box, Pagination, Skeleton, Typography, type SxProps, type Theme,
} from "@mui/material";
import { type UsePaginationProps } from "@mui/material/usePagination";

import { type PagingOptions } from "@/shared/models";

export type PageLayoutProps = PropsWithChildren<{
    handlePageChange: UsePaginationProps["onChange"];
    isPending: boolean;
    pagingOptions: PagingOptions;
    skeletonSx?: SxProps<Theme>;
    title?: string;
}>;

const NOTHING_FOUND_TEXT = "Nothing found";

export const DEFAULT_ITEM_SX: SxProps<Theme> = {
    mb: 2, minWidth: 275,
};

const DEFAULT_SKELETON_SX: SxProps<Theme> = {
    ...DEFAULT_ITEM_SX, bgcolor: "grey.200", minHeight: 150,
};

export const PageLayout = memo(({
    children,
    isPending,
    handlePageChange,
    pagingOptions,
    skeletonSx = DEFAULT_SKELETON_SX,
    title,
}: PageLayoutProps) => {
    const { page, size, totalElements } = pagingOptions;

    const resultSkeletonSx = useMemo(() => ({
        ...DEFAULT_SKELETON_SX,
        ...skeletonSx,
    }), [skeletonSx]);

    const skeletons = useMemo(() => Array.from(Array(size)).map((item, index) => (
        // eslint-disable-next-line react/no-array-index-key
        <Skeleton key={index} variant="rectangular" sx={resultSkeletonSx} />
    )), [resultSkeletonSx, size]);

    const paginationCount = Math.ceil(totalElements / size);

    return (
        <Box sx={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-between",
            height: "100%",
        }}
        >
            <Box>
                {title && (
                    <Typography variant="h5" gutterBottom>{title}</Typography>
                )}

                {totalElements === 0 && (
                    <Box>
                        <Typography>
                            {NOTHING_FOUND_TEXT}
                        </Typography>
                    </Box>
                )}

                <Box>
                    {isPending ? skeletons : children}
                </Box>
            </Box>

            {paginationCount > 0 && (
                <Box sx={{ display: "flex", justifyContent: "center" }}>
                    <Pagination
                        count={paginationCount}
                        page={page}
                        onChange={handlePageChange}
                    />
                </Box>
            )}
        </Box>
    );
});
