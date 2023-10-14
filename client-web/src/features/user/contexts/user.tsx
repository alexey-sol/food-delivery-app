import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect, useState,
} from "react";
import { useNavigate } from "react-router-dom";

import { getUseContextOrThrowError } from "@/shared/utils/helpers/context";
import { useAuthContext } from "@/features/auth/contexts/auth";
import { useLazyGetCartsByUserIdQuery, useSaveCartItemMutation } from "@/features/cart/services/api";
import { type SaveCartItemArg } from "@/features/cart/services/api/types";
import { type Cart } from "@/features/cart/models";
import { useCreateOrderMutation, useLazyGetOrdersByUserIdQuery } from "@/features/order/services/api";
import { url } from "@/shared/const";
import { type CreateOrderArg } from "@/features/order/services/api/types";
import { type Order } from "@/features/order/models";

import { type User } from "../models";

const INITIAL_CARTS: Cart[] = [];

const useCarts = ({ profile }: { profile?: User }) => {
    const userId = profile?.id;

    const [getCartsByUserId, resultOfGet] = useLazyGetCartsByUserIdQuery();

    const [saveCartItemMutation, resultOfSave] = useSaveCartItemMutation();

    const getCarts = useCallback(() => {
        if (userId) {
            getCartsByUserId(userId);
        }
    }, [userId]);

    const [carts, setCarts] = useState<Cart[]>(INITIAL_CARTS);

    const savedCartIndex = carts?.findIndex((cart) => cart.id === resultOfSave.data?.id);

    useEffect(() => {
        if (resultOfGet.data) {
            setCarts(resultOfGet.data);
        }
    }, [savedCartIndex, resultOfSave.data, resultOfGet.data, profile?.carts]);

    const saveCartItem = useCallback((arg: Omit<SaveCartItemArg, "userId">) => {
        if (userId) {
            saveCartItemMutation({ userId, ...arg });
        }
    }, [userId]);

    const isPending = resultOfSave.isLoading;

    const isPendingFor = useCallback(
        (productId: number) => resultOfSave.isLoading && resultOfSave.originalArgs?.productId === productId,
        [resultOfSave.data, resultOfSave.isLoading],
    );

    const getCartByPlaceId = (placeId: number) => carts.find((cart) => cart.place.id === placeId);

    return useMemo(() => ({
        carts,
        getCarts,
        getCartByPlaceId,
        isPending,
        isPendingFor,
        saveCartItem,
    }), [carts, getCarts, getCartByPlaceId, isPending, isPendingFor, saveCartItem]);
};

const INITIAL_ORDERS: Order[] = [];

const useOrders = ({ profile }: { profile?: User }) => {
    const navigate = useNavigate();

    const userId = profile?.id;

    const [getOrdersByUserId, resultOfGet] = useLazyGetOrdersByUserIdQuery();

    const [createOrderMutation, resultOfCreate] = useCreateOrderMutation();

    const getOrders = useCallback(() => {
        if (userId) {
            getOrdersByUserId(userId);
        }
    }, [userId]);

    const createOrder = useCallback((arg: Pick<CreateOrderArg, "placeId" | "orderItems">) => {
        if (userId) {
            createOrderMutation({ userId, ...arg });
        }
    }, [userId]);

    const isPending = resultOfCreate.isLoading;

    useEffect(() => {
        if (resultOfCreate.data) {
            navigate(`/${url.ORDER}`);
        }
    }, [resultOfCreate.data]);

    return useMemo(() => ({
        createOrder,
        getOrders,
        isPending,
        orders: resultOfGet.data ?? INITIAL_ORDERS,
    }), [createOrder, getOrders, isPending, resultOfGet.data]);
};

type Value = {
    carts: ReturnType<typeof useCarts>;
    orders: ReturnType<typeof useOrders>;
};

export const UserContext = React.createContext<Value | null>(null);

export const UserProvider: FC<PropsWithChildren> = ({ children }) => {
    const { profile } = useAuthContext();

    const carts = useCarts({ profile });
    const orders = useOrders({ profile });

    const value = useMemo(() => ({
        carts,
        orders,
    }), [carts, orders]);

    return (
        <UserContext.Provider value={value}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = getUseContextOrThrowError(UserContext);
