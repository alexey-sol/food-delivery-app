import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect, useState,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import { useAuthContext } from "features/auth/contexts/auth";
import { useLazyGetCartsByUserIdQuery, useSaveCartItemMutation } from "features/cart/services/api";
import type { SaveCartItemArg } from "features/cart/services/api/types";
import type { Cart } from "features/cart/models";
import type { User } from "../models";

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

    const saveCartItem = useCallback((arg: Pick<SaveCartItemArg, "quantity" | "productId" | "storeId">) => {
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

    const getCartByStoreId = (storeId: number) => carts.find((cart) => cart.store.id === storeId);

    return {
        carts,
        getCarts,
        getCartByStoreId,
        isPending,
        isPendingFor,
        saveCartItem,
    };
};

type Value = {
    carts: ReturnType<typeof useCarts>;
    // user: ReturnType<typeof useUser>;
};

export const UserContext = React.createContext<Value | null>(null);

export const UserProvider: FC<PropsWithChildren> = ({ children }) => {
    const { profile } = useAuthContext(); // TODO not ideal: using ctx inside another ctx

    const carts = useCarts({ profile });

    const value = useMemo(() => ({
        carts,
    }), [carts]);

    return (
        <UserContext.Provider value={value}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = getUseContextOrThrowError(UserContext);
