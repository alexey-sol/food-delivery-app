import { combineReducers } from "@reduxjs/toolkit";

import { storeApi } from "features/store/services/api";
import { storeReducer } from "features/store/slice";

export const rootReducer = combineReducers({
    [storeApi.reducerPath]: storeApi.reducer,
    store: storeReducer,
});
