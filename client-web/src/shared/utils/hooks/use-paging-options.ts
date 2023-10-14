import { useCallback, useEffect, useMemo } from "react";
import { useSearchParams } from "react-router-dom";

import { pagingOptions as initialPagingOptions, url } from "@/shared/const";
import { type PagingOptions } from "@/shared/models";

type UsePagingOptionsArg = {
    initialOptions: PagingOptions;
    setPagingOptions: (options: Partial<PagingOptions>) => void;
};

export const usePagingOptions = ({ initialOptions, setPagingOptions }: UsePagingOptionsArg) => {
    const [searchParams] = useSearchParams();

    const currentPage = searchParams.get(url.PAGE);

    const setTotalElements = useCallback((totalElements: number) =>
        setPagingOptions({ totalElements }), [setPagingOptions]);

    const page = currentPage
        ? +currentPage
        : initialPagingOptions.page;

    const pagingOptions: PagingOptions = useMemo(
        () => ({ ...initialOptions, page }),
        [initialOptions, page],
    );

    useEffect(() => {
        setPagingOptions({ page });
    }, [setPagingOptions, page]);

    return {
        pagingOptions,
        setTotalElements,
    };
};
