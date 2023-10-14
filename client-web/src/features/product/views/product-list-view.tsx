import React, {
    type FC, memo, useCallback, useEffect,
} from "react";
import { useSearchParams } from "react-router-dom";

import { ProductList } from "@/features/product/components/product-list";
import { PageLayout, type PageLayoutProps } from "@/shared/components/layout";
import { url } from "@/shared/const";
import { useUserContext } from "@/features/user/contexts/user";
import { PlacePageProvider, usePlacePageContext } from "@/features/place/contexts/place-page";

import { ProductPageProvider, useProductPageContext } from "../contexts/product-page";

type HandlePageChange = NonNullable<PageLayoutProps["handlePageChange"]>;

export const ProductListView: FC = () => {
    const { carts } = useUserContext();
    const { isPending, pagingOptions } = useProductPageContext();
    const { currentPlace } = usePlacePageContext();
    const [, setSearchParams] = useSearchParams();

    const handlePageChange = useCallback<HandlePageChange>((event, newPage) => {
        setSearchParams({ [url.PAGE]: String(newPage) });
    }, [setSearchParams]);

    useEffect(() => {
        carts.getCarts();
    }, [carts.getCarts]);

    return (
        <PageLayout
            handlePageChange={handlePageChange}
            isPending={isPending}
            pagingOptions={pagingOptions}
            skeletonSx={{ minHeight: 122 }}
            title={currentPlace?.name}
        >
            <ProductList />
        </PageLayout>
    );
};

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <PlacePageProvider>
        <ProductPageProvider>
            <ProductListView />
        </ProductPageProvider>
    </PlacePageProvider>
));
