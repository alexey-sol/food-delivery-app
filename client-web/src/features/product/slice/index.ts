import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

import { pagingOptions } from "@/shared/const";
import type { PagingOptions } from "@/shared/models";

export type ProductState = {
    pagingOptions: PagingOptions;
};

const initialState: ProductState = {
    pagingOptions,
};

export const productSlice = createSlice({
    name: "product",
    initialState,
    reducers: {
        setPagingOptions: (state, action: PayloadAction<Partial<PagingOptions>>) => {
            state.pagingOptions = {
                ...state.pagingOptions,
                ...action.payload,
            };
        },
    },
});

export const { setPagingOptions } = productSlice.actions;

export const productReducer = productSlice.reducer;
