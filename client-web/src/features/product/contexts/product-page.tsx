import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";
import { getUseContextOrThrowError } from "@/shared/utils/helpers/context";
import { useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "@/app/store/hooks";
import { PagingOptions } from "@/shared/models";
import { usePagingOptions } from "@/shared/utils/hooks/use-paging-options";

import { useGetProductsByPlaceIdQuery } from "../services/api";
import { selectPagingOptions } from "../slice/selectors";
import { setPagingOptions } from "../slice";
import { GetProductsByPlaceIdArg } from "../services/api/types";
import type { ProductPreview } from "../models";

const INITIAL_PRODUCTS: ProductPreview[] = [];

type UseProductsArg = ReturnType<typeof usePagingOptions>;

const useProducts = ({ pagingOptions, setTotalElements }: UseProductsArg) => {
    const params = useParams();
    const { page, size } = pagingOptions;

    const placeId = params.placeId
        ? +params.placeId
        : undefined;

    const getProductsByIdArg: GetProductsByPlaceIdArg = useMemo(
        () => ({
            paging: { page, size },
            placeId,
        }),
        [page, size, placeId],
    );

    const resultOfGet = useGetProductsByPlaceIdQuery(getProductsByIdArg, {
        skip: !placeId,
        selectFromResult: ({ data, isFetching }) => ({
            isFetching,
            products: data?.content ?? INITIAL_PRODUCTS,
            totalElements: data?.totalElements ?? pagingOptions.totalElements,
        }),
    });

    const { isFetching, totalElements, products } = resultOfGet;

    useEffect(() => {
        setTotalElements(totalElements);
    }, [totalElements, setTotalElements]);

    return {
        isPending: isFetching,
        products,
    };
};

type UseProductPageResult = {
    isPending: boolean;
    pagingOptions: PagingOptions;
    products: ProductPreview[];
};

export const ProductPageContext = React.createContext<UseProductPageResult | null>(null);

export const ProductPageProvider: FC<PropsWithChildren> = ({ children }) => {
    const initialOptions = useAppSelector(selectPagingOptions);

    const dispatch = useAppDispatch();
    const onSetPagingOptions = useCallback((options: Partial<PagingOptions>) => {
        dispatch(setPagingOptions(options));
    }, [dispatch]);

    const { pagingOptions, setTotalElements } = usePagingOptions({
        initialOptions,
        setPagingOptions: onSetPagingOptions,
    });
    const { isPending, products } = useProducts({ pagingOptions, setTotalElements });

    const value = useMemo(() => ({
        isPending,
        pagingOptions,
        products,
    }), [isPending, pagingOptions, products]);

    return (
        <ProductPageContext.Provider value={value}>
            {children}
        </ProductPageContext.Provider>
    );
};

export const useProductPageContext = getUseContextOrThrowError(ProductPageContext);
