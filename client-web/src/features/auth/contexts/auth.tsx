import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import type { User } from "features/user/models";
import { useAppDispatch } from "app/store/hooks";
import { useNavigate } from "react-router-dom";
import { cartApi } from "features/cart/services/api";
import { orderApi } from "features/order/services/api";
import type { Locality, SignInDto, SignUpDto } from "../models";
import {
    authApi, useGetProfileQuery, useSignInMutation, useSignOutMutation, useSignUpMutation,
} from "../services/api";

const INITIAL_CITIES: Locality[] = [];

export type AuthPending = "get-profile" | "sign-in" | "sign-up" | "sign-out";

export type UseAuthApiResult = {
    cities: Locality[];
    profile?: User;
    signIn: (arg: SignInDto) => void;
    signOut: () => void;
    signUp: (arg: SignUpDto) => void;
};

export const useAuth = (): UseAuthApiResult => {
    const navigate = useNavigate();

    const dispatch = useAppDispatch();
    const resetAuthState = useCallback(() => {
        dispatch(authApi.util.resetApiState());
        dispatch(cartApi.util.resetApiState());
        dispatch(orderApi.util.resetApiState());
    }, [dispatch]);

    const getProfileResult = useGetProfileQuery(undefined);

    const [signIn, signInResult] = useSignInMutation();

    const [signOut, signOutResult] = useSignOutMutation();

    const [signUp, signUpResult] = useSignUpMutation();

    useEffect(() => {
        const signedOut = !!signOutResult.data;

        if (signedOut) {
            resetAuthState();
        }
    }, [resetAuthState, signOutResult.data]);

    const cities = useMemo(
        () => getProfileResult.data?.cities ?? INITIAL_CITIES,
        [getProfileResult.data],
    );

    const profile = useMemo(
        () => getProfileResult.data?.profile ?? signInResult.data ?? signUpResult.data,
        [getProfileResult.data, signInResult.data, signUpResult.data],
    );

    return useMemo(() => ({
        cities,
        profile,
        signIn,
        signOut,
        signUp,
    }), [cities, profile, signIn, signOut, signUp]);
};

type Value = ReturnType<typeof useAuth>;

export const AuthContext = React.createContext<Value | null>(null);

export const AuthProvider: FC<PropsWithChildren> = ({ children }) => {
    const value = useAuth();

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuthContext = getUseContextOrThrowError(AuthContext);
