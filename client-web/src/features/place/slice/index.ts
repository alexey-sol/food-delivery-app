import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

import { pagingOptions } from "@/shared/const";
import { type PagingOptions } from "@/shared/models";

export type PlaceState = {
    pagingOptions: PagingOptions;
};

const initialState: PlaceState = {
    pagingOptions,
};

export const placeSlice = createSlice({
    name: "place",
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

export const { setPagingOptions } = placeSlice.actions;

export const placeReducer = placeSlice.reducer;
