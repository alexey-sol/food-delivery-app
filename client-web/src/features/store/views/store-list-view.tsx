import React, { type FC, memo, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { StoreList } from "features/store/components/store-list";
import { PageLayout, type PageLayoutProps } from "shared/components/layout";
import { url } from "shared/const";
import { StorePageProvider, useStorePageContext } from "../contexts/store-page";

type HandlePageChange = NonNullable<PageLayoutProps["handlePageChange"]>;

export const StoreListView: FC = () => {
    const { isPending, pagingOptions } = useStorePageContext();

    const [, setSearchParams] = useSearchParams();

    const handlePageChange = useCallback<HandlePageChange>((event, newPage) => {
        setSearchParams({ [url.PAGE]: String(newPage) });
    }, [setSearchParams]);

    return (
        <PageLayout
            handlePageChange={handlePageChange}
            isPending={isPending}
            pagingOptions={pagingOptions}
            skeletonSx={{ minHeight: 122 }}
        >
            <StoreList />
        </PageLayout>
    );
};

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <StorePageProvider>
        <StoreListView />
    </StorePageProvider>

));
