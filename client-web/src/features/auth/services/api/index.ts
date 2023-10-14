import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

import { type InitDto, type SignInDto, type SignUpDto } from "@/features/auth/models";
import { type User } from "@/features/user/models";
import { resources, url } from "@/shared/const";
import { getApiPath } from "@/shared/utils/formatters/api-path";

const baseUrl = getApiPath(url.API);

export const authApi = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getProfile: builder.query<InitDto, void>({
            query: () => ({
                url: `${resources.AUTH}/profile`,
            }),
        }),
        signIn: builder.mutation<User, SignInDto>({
            query: (body) => ({
                body,
                method: "POST",
                url: `${resources.AUTH}/sign-in`,
            }),
        }),
        signOut: builder.mutation<boolean, void>({
            query: () => ({
                method: "POST",
                url: `${resources.AUTH}/sign-out`,
            }),
        }),
        signUp: builder.mutation<User, SignUpDto>({
            query: (body) => ({
                body,
                method: "POST",
                url: `${resources.AUTH}/sign-up`,
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
