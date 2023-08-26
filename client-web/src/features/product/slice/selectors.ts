import type { RootState } from "app/store";
import type { ProductState } from ".";

export const selectPagingOptions = (state: RootState): ProductState["pagingOptions"] =>
    state.product.pagingOptions;
