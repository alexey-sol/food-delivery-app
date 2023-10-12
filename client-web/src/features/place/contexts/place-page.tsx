import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";

import { getUseContextOrThrowError } from "@/shared/utils/helpers/context";
import { useAppDispatch, useAppSelector } from "@/app/store/hooks";
import { PagingOptions } from "@/shared/models";
import { usePagingOptions } from "@/shared/utils/hooks/use-paging-options";

import { useAuth } from "@/features/auth/contexts/auth";
import { useParams } from "react-router-dom";
import { useGetPlacesByLocalityIdQuery } from "../services/api";
import { selectPagingOptions } from "../slice/selectors";
import { setPagingOptions } from "../slice";
import { GetPlacesByIdArg } from "../services/api/types";
import type { PlacePreview } from "../models";

const INITIAL_PLACES: PlacePreview[] = [];

type UsePlacesArg = ReturnType<typeof usePagingOptions>;

const usePlaces = ({ pagingOptions, setTotalElements }: UsePlacesArg) => {
    const params = useParams();

    const placeId = params.placeId // TODO duplicated from product-page
        ? +params.placeId
        : undefined;

    const { profile } = useAuth();

    const locality = profile?.address.locality;
    const localityId = locality?.id;

    const { page, size } = pagingOptions;

    const getPlacesByIdArg: GetPlacesByIdArg = useMemo(
        () => ({ localityId, paging: { page, size } }),
        [localityId, page, size],
    );

    const resultOfGet = useGetPlacesByLocalityIdQuery(getPlacesByIdArg, {
        skip: !localityId,
        selectFromResult: ({ data, isFetching }) => ({
            isFetching,
            places: data?.content ?? INITIAL_PLACES,
            totalElements: data?.totalElements ?? pagingOptions.totalElements,
        }),
    });

    const { isFetching, totalElements, places } = resultOfGet;

    const currentPlace = useMemo(
        () => (placeId ? places.find((place) => place.id === placeId) : undefined),
        [placeId, places],
    );

    useEffect(() => {
        setTotalElements(totalElements);
    }, [totalElements, setTotalElements]);

    return {
        locality,
        currentPlace,
        isPending: isFetching,
        places,
    };
};

type UsePlacePageResult = ReturnType<typeof usePlaces> & {
    pagingOptions: PagingOptions;
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
