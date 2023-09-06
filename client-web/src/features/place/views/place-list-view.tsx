import React, { type FC, memo, useCallback } from "react";
import { useSearchParams } from "react-router-dom";
import { PlaceList } from "features/place/components/place-list";
import { PageLayout, type PageLayoutProps } from "shared/components/layout";
import { url } from "shared/const";
import { PlacePageProvider, usePlacePageContext } from "../contexts/place-page";

type HandlePageChange = NonNullable<PageLayoutProps["handlePageChange"]>;

export const PlaceListView: FC = () => {
    const { city, isPending, pagingOptions } = usePlacePageContext();
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
            title={city?.name}
        >
            <PlaceList />
        </PageLayout>
    );
};

// eslint-disable-next-line import/no-default-export
export default memo(() => (
    <PlacePageProvider>
        <PlaceListView />
    </PlacePageProvider>
));
