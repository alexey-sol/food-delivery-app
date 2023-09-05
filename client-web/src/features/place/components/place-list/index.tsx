import React, { useMemo, type FC } from "react";
import {
    Card, CardContent, Typography, type SxProps, type Theme, Box,
} from "@mui/material";
import { usePlacePageContext } from "features/place/contexts/place-page";
import { AppLink } from "shared/components/app-link";
import { url } from "shared/const";
import { DEFAULT_ITEM_SX } from "shared/components/layout";
import type { City } from "features/auth/models";

const NO_DESCRIPTION_TEXT = "No description";

export type PlaceListProps = {
    city?: City;
    itemSx?: SxProps<Theme>;
};

export const PlaceList: FC<PlaceListProps> = ({ city, itemSx = DEFAULT_ITEM_SX }) => {
    const { places } = usePlacePageContext();

    const resultItemSx = useMemo(() => ({
        ...DEFAULT_ITEM_SX,
        ...itemSx,
    }), [itemSx]);

    const placeOverviews = places.map((place) => (
        <AppLink key={place.id} to={`${place.id}/${url.PRODUCT}`}>
            <Card key={place.id} sx={resultItemSx}>
                <CardContent>
                    <Typography variant="h5" color="text.secondary" gutterBottom>
                        {place.name}
                    </Typography>

                    <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                        {place.description ?? NO_DESCRIPTION_TEXT}
                    </Typography>

                    <Typography sx={{ fontSize: 14 }} color="text.secondary" component="section">
                        {place.address.addressLine}
                    </Typography>
                </CardContent>
            </Card>
        </AppLink>
    ));

    return (
        <Box>
            {city && (
                <Typography gutterBottom variant="h5">{city.name}</Typography>
            )}

            <Box>
                {placeOverviews}
            </Box>
        </Box>
    );
};
