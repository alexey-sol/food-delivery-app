import React, {
    type FC, memo, useCallback, useEffect,
} from "react";
import { useParams, useSearchParams } from "react-router-dom";
import { ProductList } from "features/product/components/product-list";
import { PageLayout, type PageLayoutProps } from "shared/components/layout";
import { url } from "shared/const";
import { useUserContext } from "features/user/contexts/user";
import { PlacePageContext, PlacePageProvider, usePlacePageContext } from "features/place/contexts/place-page";
import { ProductPageProvider, useProductPageContext } from "../contexts/product-page";

type HandlePageChange = NonNullable<PageLayoutProps["handlePageChange"]>;

export const ProductListView: FC = () => {
    const params = useParams();

    const { carts } = useUserContext();
    const { isPending, pagingOptions } = useProductPageContext();
    const { getPlaceById } = usePlacePageContext();
    const [, setSearchParams] = useSearchParams();

    const place = params.placeId ? getPlaceById(+params.placeId) : undefined;

    const handlePageChange = useCallback<HandlePageChange>((event, newPage) => {
        setSearchParams({ [url.PAGE]: String(newPage) });
    }, [setSearchParams]);

    // TODO should it be here?
    useEffect(() => {
        carts.getCarts();
    }, [carts.getCarts]);

    return (
        <PageLayout
            handlePageChange={handlePageChange}
            isPending={isPending}
            pagingOptions={pagingOptions}
            skeletonSx={{ minHeight: 122 }}
        >
            <ProductList place={place} />
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
