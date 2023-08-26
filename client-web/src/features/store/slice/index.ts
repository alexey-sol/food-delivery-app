import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

import { defaults } from "shared/const";
import type { PagingOptions } from "shared/models";

export type StoreState = {
    pagingOptions: PagingOptions;
};

const initialState: StoreState = {
    pagingOptions: {
        page: defaults.PAGING_PAGE,
        size: defaults.PAGING_SIZE,
        totalElements: 0,
    },
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
