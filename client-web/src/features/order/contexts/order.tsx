import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import { useAuthContext } from "features/auth/contexts/auth";
import type { User } from "features/user/models";
import type { Order } from "../models";
import { useCreateOrderMutation, useLazyGetAllOrdersByUserIdQuery } from "../services/api";
import { CreateOrderArg } from "../services/api/types";
import { useNavigate } from "react-router-dom";
import { url } from "shared/const";

const INITIAL_ORDERS: Order[] = [];

const useOrders = ({ profile }: { profile?: User }) => {
    const navigate = useNavigate()

    // TODO make auth redux slice, select userId from there
    // const resultOfGet = useGetCartsByUserIdQuery(userId);
    const userId = profile?.id;

    const [getOrdersByUserId, resultOfGet] = useLazyGetAllOrdersByUserIdQuery();
    // const resultOfGet = useGetAllOrdersByUserIdQuery(userId, {
    //     skip: !userId,
    // });

    const [createOrderMutation, resultOfCreate] = useCreateOrderMutation();

    const getOrders = useCallback(() => {
        if (userId) {
            getOrdersByUserId(userId);
        }
    }, [userId]);

    // const orders = profile?.orders ?? INITIAL_ORDERS;
    // const orders = resultOfGet.data ?? profile?.orders ?? INITIAL_ORDERS;

    // const createOrder = useCallback((arg: CreateOrderArg["orderItems"]) => {
    const createOrder = useCallback((arg: Pick<CreateOrderArg, "storeId" | "orderItems">) => {
        if (userId) {
            createOrderMutation({ userId, ...arg });
        }
    }, [userId]);

    // resultOfCreate.data - there wil be 1 cart which i shoud add to cart list with replacing the old cart
    // const orders = resultOfGet.data; // TODO plural: orders
    const isPending = resultOfCreate.isLoading;

    useEffect(() => {
        if (resultOfCreate.data) {
            navigate(`/${url.ORDER}`)
        }
    }, [resultOfCreate.data]);

    return {
        // orders,
        createOrder,
        getOrders,
        isPending,
        orders: resultOfGet.data ?? INITIAL_ORDERS,
    };
};

type Value = {
    orders: ReturnType<typeof useOrders>;
    // user: ReturnType<typeof useUser>;
};

export const OrderContext = React.createContext<Value | null>(null);

export const OrderProvider: FC<PropsWithChildren> = ({ children }) => {
    const { profile } = useAuthContext(); // TODO not ideal: using ctx inside another ctx

    const orders = useOrders({ profile });

    const value = useMemo(() => ({
        orders,
    }), [orders]);

    return (
        <OrderContext.Provider value={value}>
            {children}
        </OrderContext.Provider>
    );
};

export const useOrderContext = getUseContextOrThrowError(OrderContext);
