import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";

import { getUseContextOrThrowError } from "shared/utils/helpers/context";
import { useAppDispatch, useAppSelector } from "app/store/hooks";
import { PagingOptions } from "shared/models";
import { usePagingOptions } from "shared/utils/hooks/use-paging-options";

import { useAuth } from "features/auth/contexts/auth";
import { useGetStoresQuery } from "../services/api";
import { selectPagingOptions } from "../slice/selectors";
import { setPagingOptions } from "../slice";
import { GetStoresArg } from "../services/api/types";
import type { StorePreview } from "../models";

const INITIAL_STORES: StorePreview[] = [];

type UseStoresArg = ReturnType<typeof usePagingOptions>;

const useStores = ({ pagingOptions, setTotalElements }: UseStoresArg) => {
    const { profile } = useAuth();
    const cityId = profile?.address.city.id;

    const { page, size } = pagingOptions;

    const getStoresArg: GetStoresArg = useMemo(
        () => ({ cityId, paging: { page, size } }),
        [cityId, page, size],
    );

    const resultOfGet = useGetStoresQuery(getStoresArg, {
        selectFromResult: ({ data, isFetching }) => ({
            isFetching,
            stores: data?.content ?? INITIAL_STORES,
            totalElements: data?.totalElements ?? pagingOptions.totalElements,
        }),
    });

    const { isFetching, totalElements, stores } = resultOfGet;

    useEffect(() => {
        setTotalElements(totalElements);
    }, [totalElements, setTotalElements]);

    return {
        isPending: isFetching,
        stores,
    };
};

type UseStorePageResult = {
    isPending: boolean;
    pagingOptions: PagingOptions;
    stores: StorePreview[];
};

export const StorePageContext = React.createContext<UseStorePageResult | null>(null);

export const StorePageProvider: FC<PropsWithChildren> = ({ children }) => {
    const initialOptions = useAppSelector(selectPagingOptions);

    const dispatch = useAppDispatch();
    const onSetPagingOptions = useCallback((options: Partial<PagingOptions>) => {
        dispatch(setPagingOptions(options));
    }, [dispatch]);

    const { pagingOptions, setTotalElements } = usePagingOptions({
        initialOptions,
        setPagingOptions: onSetPagingOptions,
    });
    const { isPending, stores } = useStores({ pagingOptions, setTotalElements });

    const value = useMemo(() => ({
        isPending,
        pagingOptions,
        stores,
    }), [isPending, pagingOptions, stores]);

    return (
        <StorePageContext.Provider value={value}>
            {children}
        </StorePageContext.Provider>
    );
};

export const useStorePageContext = getUseContextOrThrowError(StorePageContext);
