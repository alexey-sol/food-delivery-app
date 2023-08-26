import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";

import { getUseContextOrThrowError } from "shared/utils/helpers/context";
import { useSearchParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "app/store/hooks";
import { defaults, url } from "shared/const";
import { PagingOptions } from "shared/models";
import { useGetStoresQuery } from "../services/api";
import type { StorePreview } from "../models";
import { selectPagingOptions } from "../slice/selectors";
import { setPagingOptions } from "../slice";
import { GetStoresArg } from "../services/api/types";

const DEFAULT_STORES: StorePreview[] = [];

const usePagingOptions = () => {
    const [searchParams] = useSearchParams();

    const initialPage = searchParams.get(url.PAGE);

    const initialOptions = useAppSelector(selectPagingOptions);

    const dispatch = useAppDispatch();
    const onSetPagingOptions = useCallback((options: Partial<PagingOptions>) => {
        dispatch(setPagingOptions(options));
    }, [dispatch]);

    const setTotalElements = useCallback((newTotalElements: number) =>
        onSetPagingOptions({ totalElements: newTotalElements }), [onSetPagingOptions]);

    const page = initialPage
        ? +initialPage
        : defaults.PAGING_PAGE;

    const pagingOptions: PagingOptions = useMemo(
        () => ({ ...initialOptions, page }),
        [initialOptions, page],
    );

    useEffect(() => {
        onSetPagingOptions({ page });
    }, [onSetPagingOptions, page]);

    return useMemo(() => ({
        pagingOptions,
        setTotalElements,
    }), [pagingOptions, setTotalElements]);
};

type UseStoresArg = ReturnType<typeof usePagingOptions>;

const useStores = ({ pagingOptions, setTotalElements }: UseStoresArg) => {
    const { page, size } = pagingOptions;

    const getStoresArg: GetStoresArg = useMemo(
        () => ({ paging: { page, size } }),
        [page, size],
    );

    const resultOfGet = useGetStoresQuery(getStoresArg, {
        selectFromResult: ({ data, isFetching }) => ({
            isFetching,
            stores: data?.content ?? DEFAULT_STORES,
            totalElements: data?.totalElements ?? pagingOptions.totalElements,
        }),
    });

    const { isFetching, totalElements, stores } = resultOfGet;

    useEffect(() => {
        setTotalElements(totalElements);
    }, [totalElements, setTotalElements]);

    return useMemo(() => ({
        isPending: isFetching,
        stores,
    }), [isFetching, stores]);
};

type UseStorePageResult = {
    isPending: boolean;
    pagingOptions: PagingOptions;
    stores: StorePreview[];
};

export const StorePageContext = React.createContext<UseStorePageResult | null>(null);

export const StorePageProvider: FC<PropsWithChildren> = ({ children }) => {
    const { pagingOptions, setTotalElements } = usePagingOptions();
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

export const useStorePageContext = getUseContextOrThrowError<UseStorePageResult>(StorePageContext);
