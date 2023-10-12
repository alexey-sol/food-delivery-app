import type { RootState } from "@/app/store";
import type { PlaceState } from ".";

export const selectPagingOptions = (state: RootState): PlaceState["pagingOptions"] =>
    state.place.pagingOptions;
