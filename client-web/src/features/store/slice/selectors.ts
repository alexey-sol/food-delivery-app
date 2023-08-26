import type { RootState } from "app/store";
import type { StoreState } from ".";

export const selectPagingOptions = (state: RootState): StoreState["pagingOptions"] =>
    state.store.pagingOptions;
