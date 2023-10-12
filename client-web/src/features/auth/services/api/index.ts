import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "@/app/app-config";
import type { InitDto, SignInDto, SignUpDto } from "@/features/auth/models";
import type { User } from "@/features/user/models";

import { baseUrl } from "./utils";

const { authResource } = appConfig;

export const authApi = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getProfile: builder.query<InitDto, void>({
            query: () => ({
                url: `${authResource}/profile`,
            }),
        }),
        signIn: builder.mutation<User, SignInDto>({
            query: (body) => ({
                body,
                method: "POST",
                url: `${authResource}/sign-in`,
            }),
        }),
        signOut: builder.mutation<boolean, void>({
            query: () => ({
                method: "POST",
                url: `${authResource}/sign-out`,
            }),
        }),
        signUp: builder.mutation<User, SignUpDto>({
            query: (body) => ({
                body,
                method: "POST",
                url: `${authResource}/sign-up`,
            }),
        }),
    }),
});

export const {
    useGetProfileQuery,
    useSignInMutation,
    useSignOutMutation,
    useSignUpMutation,
} = authApi;
