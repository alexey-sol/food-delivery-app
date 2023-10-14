import { type RootState } from "@/app/store";

import { type FeedbackState } from ".";

export const selectSnackbar = (state: RootState): FeedbackState["snackbar"] =>
    state.feedback.snackbar;
