import React, { useMemo, type FC } from "react";
import {
    Card, CardContent, Typography, type SxProps, type Theme,
} from "@mui/material";
import { useStorePageContext } from "features/store/contexts/store-page";
import { AppLink } from "shared/components/app-link";
import { url } from "shared/const";
import { DEFAULT_ITEM_SX } from "shared/components/layout";

const NO_DESCRIPTION_TEXT = "No description";

export type StoreListProps = {
    itemSx?: SxProps<Theme>;
};

export const StoreList: FC<StoreListProps> = ({ itemSx = DEFAULT_ITEM_SX }) => {
    const { stores } = useStorePageContext();

    const resultItemSx = useMemo(() => ({
        ...DEFAULT_ITEM_SX,
        ...itemSx,
    }), [itemSx]);

    const storeOverviews = stores.map((store) => (
        <AppLink key={store.id} to={`${store.id}/${url.PRODUCT}`}>
            <Card key={store.id} sx={resultItemSx}>
                <CardContent>
                    <Typography variant="h5" color="text.secondary" gutterBottom>
                        {store.name}
                    </Typography>

                    <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                        {store.description ?? NO_DESCRIPTION_TEXT}
                    </Typography>

                    <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                        {store.address.addressLine}
                    </Typography>
                </CardContent>
            </Card>
        </AppLink>
    ));

    return (
        <section>
            {storeOverviews}
        </section>
    );
};
