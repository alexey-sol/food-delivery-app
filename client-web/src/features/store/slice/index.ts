import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

import { pagingOptions } from "shared/const";
import type { PagingOptions } from "shared/models";

export type StoreState = {
    pagingOptions: PagingOptions;
};

const initialState: StoreState = {
    pagingOptions,
};

export const storeSlice = createSlice({
    name: "store",
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

export const { setPagingOptions } = storeSlice.actions;

export const storeReducer = storeSlice.reducer;