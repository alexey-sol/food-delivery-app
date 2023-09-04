import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect, useState,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import { useAuthContext } from "features/auth/contexts/auth";
import { useLazyGetCartsByUserIdQuery, useSaveCartItemMutation } from "features/cart/services/api";
import type { SaveCartItemArg } from "features/cart/services/api/types";
import type { Cart } from "features/cart/models";
import type { User } from "../models";
import { useNavigate } from "react-router-dom";
import { useCreateOrderMutation, useLazyGetAllOrdersByUserIdQuery } from "features/order/services/api";
import { url } from "shared/const";
import type { CreateOrderArg } from "features/order/services/api/types";
import type { Order } from "features/order/models";

const INITIAL_CARTS: Cart[] = [];

const useCarts = ({ profile }: { profile?: User }) => {
    // TODO make auth redux slice, select userId from there
    // const resultOfGet = useGetCartsByUserIdQuery(userId);
    const userId = profile?.id;

    const [getCartsByUserId, resultOfGet] = useLazyGetCartsByUserIdQuery();

    const [saveCartItemMutation, resultOfSave] = useSaveCartItemMutation();

    const getCarts = useCallback(() => {
        if (userId) {
            getCartsByUserId(userId);
        }
    }, [userId]);

    // const carts = profile?.carts ?? INITIAL_CARTS;
    // const carts = resultOfGet.data ?? profile?.carts ?? INITIAL_CARTS;

    const [carts, setCarts] = useState<Cart[]>(INITIAL_CARTS);

    const savedCartIndex = carts?.findIndex((cart) => cart.id === resultOfSave.data?.id);

    useEffect(() => {
        // if (resultOfSave.data) {


        //     console.log(savedCartIndex, resultOfSave.data, carts)
        //     if (savedCartIndex >= 0) {
        //         const updatedCarts = [...carts];
        //         // updatedCarts[savedCartIndex] = {...updatedCarts[savedCartIndex], ...resultOfSave.data};
        //         updatedCarts[savedCartIndex] = resultOfSave.data;
        //         setCarts(updatedCarts);
        //     }
        // }
        if (resultOfGet.data) {
            setCarts(resultOfGet.data);
        }
        // else if (profile?.carts) {
        //     setCarts(profile?.carts);
        // }
    }, [savedCartIndex, resultOfSave.data, resultOfGet.data, profile?.carts]);

    // useEffect(() => {
    //     if (resultOfSave.data) {
    //         const savedCartIndex = carts?.findIndex((cart) => cart.id === resultOfSave.data?.id);

    //         if (savedCartIndex >= 0) {
    //             const updatedCarts = [...carts];
    //             updatedCarts[savedCartIndex] = resultOfSave.data;
    //             setCarts(updatedCarts);
    //         }
    //     }
    // }, [carts, resultOfSave.data]);

    const saveCartItem = useCallback((arg: Pick<SaveCartItemArg, "quantity" | "productId" | "placeId">) => {
        if (userId) {
            saveCartItemMutation({ userId, ...arg });
        }
    }, [userId]);

    // resultOfSave.data - there wil be 1 cart which i shoud add to cart list with replacing the old cart
    // const carts = resultOfGet.data; // TODO plural: carts
    const isPending = resultOfSave.isLoading;

    // TODO
    const isPendingFor = useCallback(
        (productId: number) => resultOfSave.isLoading && resultOfSave.originalArgs?.productId === productId,
        [resultOfSave.data, resultOfSave.isLoading],
    );

    const getCartByPlaceId = (placeId: number) => carts.find((cart) => cart.place.id === placeId); // TODO check if user id is used

    return useMemo(() => ({
        carts,
        getCarts,
        getCartByPlaceId,
        isPending,
        isPendingFor,
        saveCartItem,
    }), [carts, getCarts, getCartByPlaceId, isPending, isPendingFor, saveCartItem])
};

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
    const createOrder = useCallback((arg: Pick<CreateOrderArg, "placeId" | "orderItems">) => {
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

    return useMemo(() => ({
        createOrder,
        getOrders,
        isPending,
        orders: resultOfGet.data ?? INITIAL_ORDERS,
    }), [createOrder, getOrders, isPending, resultOfGet.data])
};

type Value = {
    carts: ReturnType<typeof useCarts>;
    orders: ReturnType<typeof useOrders>;
    // user: ReturnType<typeof useUser>;
};

export const UserContext = React.createContext<Value | null>(null);

export const UserProvider: FC<PropsWithChildren> = ({ children }) => {
    const { profile } = useAuthContext(); // TODO not ideal: using ctx inside another ctx

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
