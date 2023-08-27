import React, {
    useMemo, type FC, type PropsWithChildren, useCallback,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import { useGetCartByUserIdQuery, useSaveCartItemMutation } from "../services/api/cart-api";
import type { SaveCartItemArg } from "../services/api/types";

const useCart = () => {
    // TODO make auth redux slice, select userId from there
    const userId = 1; // TODO hardcoded

    const resultOfGet = useGetCartByUserIdQuery(userId);

    const [saveCartItemMutation, resultOfSave] = useSaveCartItemMutation();

    const saveCartItem = useCallback((arg: Pick<SaveCartItemArg, "quantity" | "productId">) => {
        saveCartItemMutation({ userId, ...arg });
    }, [userId]);

    const cart = resultOfSave.data ?? resultOfGet.data;
    const isPending = resultOfSave.isLoading ?? resultOfGet.isFetching;

    return {
        cart,
        isPending,
        saveCartItem,
    };
};

type Value = {
    cart: ReturnType<typeof useCart>;
};

export const CartContext = React.createContext<Value | null>(null);

export const CartProvider: FC<PropsWithChildren> = ({ children }) => {
    const cart = useCart();

    const value = useMemo(() => ({
        cart,
    }), [cart]);

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = getUseContextOrThrowError(CartContext);
