import React, {
    useMemo, type FC, type PropsWithChildren, useCallback, useEffect,
} from "react";
import { getUseContextOrThrowError } from "shared/utils/helpers/context";

import type { User } from "features/user/models";
import { useAppDispatch } from "app/store/hooks";
import {
    authApi, useGetProfileQuery, useSignInMutation, useSignOutMutation, useSignUpMutation,
} from "../services/api";
import type { City, SignInDto, SignUpDto } from "../models";

// TODO need this context at all? Maybe use user context?
const INITIAL_CITIES: City[] = [];

export type AuthPending = "get-profile" | "sign-in" | "sign-up" | "sign-out";

export type UseAuthApiResult = {
    cities: City[];
    // pending?: AuthPending;
    profile?: User;
    signIn: (arg: SignInDto) => void;
    signOut: () => void;
    signUp: (arg: SignUpDto) => void;
};

export const useAuth = (): UseAuthApiResult => {
    const dispatch = useAppDispatch();
    const resetAuthState = useCallback(() => dispatch(authApi.util.resetApiState()), [dispatch]);

    const getProfileResult = useGetProfileQuery(undefined); // TODO make void param

    const [signIn, signInResult] = useSignInMutation();

    const [signOut, signOutResult] = useSignOutMutation();

    const [signUp, signUpResult] = useSignUpMutation();

    // const pending = useMemo<AuthPending | undefined>(() => {
    //     if (getProfileResult.isFetching) {
    //         return "get-profile";
    //     } else if (signInResult.isLoading) {
    //         return "sign-in";
    //     } else if (signUpResult.isLoading) {
    //         return "sign-up";
    //     } else if (signOutResult.isLoading) {
    //         return "sign-out";
    //     }

    //     return undefined;
    // }, [getProfileResult.isFetching, signInResult.isLoading, signOutResult.isLoading,
    //     signUpResult.isLoading]);

    useEffect(() => {
        const signedOut = Boolean(signOutResult.data);

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
        // pending,
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
