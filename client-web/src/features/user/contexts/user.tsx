import React, {
    useMemo, type FC, type PropsWithChildren, useCallback,
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

    console.log("results: get, save", profile?.carts, resultOfSave.data); // TODO need to put saved cart to user.carts somehow. "user" should be redux slice. Or just remove carts from exposed user, provide them from here?

    // const carts = profile?.carts ?? INITIAL_CARTS;
    const carts = resultOfGet.data ?? profile?.carts ?? INITIAL_CARTS;

    const saveCartItem = useCallback((arg: Pick<SaveCartItemArg, "quantity" | "productId">) => {
        if (userId) {
            saveCartItemMutation({ userId, ...arg });
        }
    }, [userId]);

    // resultOfSave.data - there wil be 1 cart which i shoud add to cart list with replacing the old cart
    // const carts = resultOfGet.data; // TODO plural: carts
    const isPending = resultOfSave.isLoading;

    return {
        carts,
        getCarts,
        isPending,
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
