import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";

import { getUseContextOrThrowError } from "shared/utils/helpers/context";
import { useAppDispatch, useAppSelector } from "app/store/hooks";
import { PagingOptions } from "shared/models";
import { usePagingOptions } from "shared/utils/hooks/use-paging-options";

import { useAuth } from "features/auth/contexts/auth";
import { useGetPlacesQuery } from "../services/api";
import { selectPagingOptions } from "../slice/selectors";
import { setPagingOptions } from "../slice";
import { GetPlacesArg } from "../services/api/types";
import type { PlacePreview } from "../models";

const INITIAL_PLACES: PlacePreview[] = [];

type UsePlacesArg = ReturnType<typeof usePagingOptions>;

const usePlaces = ({ pagingOptions, setTotalElements }: UsePlacesArg) => {
    const { profile } = useAuth();

    const city = profile?.address.city;
    const cityId = city?.id;


    const { page, size } = pagingOptions;

    const getPlacesArg: GetPlacesArg = useMemo(
        () => ({ cityId, paging: { page, size } }),
        [cityId, page, size],
    );

    const resultOfGet = useGetPlacesQuery(getPlacesArg, {
        selectFromResult: ({ data, isFetching }) => ({
            isFetching,
            places: data?.content ?? INITIAL_PLACES,
            totalElements: data?.totalElements ?? pagingOptions.totalElements,
        }),
    });

    const { isFetching, totalElements, places } = resultOfGet;

    const getPlaceById = useCallback( // TODO rename to not confuse with API method?
        (id: number) => places.find((place) => place.id === id),
        [places],
    );

    useEffect(() => {
        setTotalElements(totalElements);
    }, [totalElements, setTotalElements]);

    return {
        city,
        getPlaceById,
        isPending: isFetching,
        places,
    };
};

type UsePlacePageResult = ReturnType<typeof usePlaces> & {
    // isPending: boolean;
    pagingOptions: PagingOptions;
    // places: PlacePreview[];
};

export const PlacePageContext = React.createContext<UsePlacePageResult | null>(null);

export const PlacePageProvider: FC<PropsWithChildren> = ({ children }) => {
    const initialOptions = useAppSelector(selectPagingOptions);

    const dispatch = useAppDispatch();
    const onSetPagingOptions = useCallback((options: Partial<PagingOptions>) => {
        dispatch(setPagingOptions(options));
    }, [dispatch]);

    const { pagingOptions, setTotalElements } = usePagingOptions({
        initialOptions,
        setPagingOptions: onSetPagingOptions,
    });
    const places = usePlaces({ pagingOptions, setTotalElements });

    const value = useMemo(() => ({
        // isPending,
        pagingOptions,
        ...places,
    }), [pagingOptions, places]);

    return (
        <PlacePageContext.Provider value={value}>
            {children}
        </PlacePageContext.Provider>
    );
};

export const usePlacePageContext = getUseContextOrThrowError(PlacePageContext);
