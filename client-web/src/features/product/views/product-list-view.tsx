import React, { type FC, memo, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { ProductList } from "features/product/components/product-list";
import { PageLayout, type PageLayoutProps } from "shared/components/layout";
import { url } from "shared/const";
import { ProductPageProvider, useProductPageContext } from "../contexts/product-page";

type HandlePageChange = NonNullable<PageLayoutProps["handlePageChange"]>;

export const ProductListView: FC = () => {
    const { isPending, pagingOptions } = useProductPageContext();
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
            <ProductList />
        </PageLayout>
    );
};

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <ProductPageProvider>
        <ProductListView />
    </ProductPageProvider>
));
