import { type Context, useContext } from "react";

import { ContextOutsideProviderError } from "@/shared/errors";

const useCustomContext = <T>(Ctx: Context<T | null>): T | never => {
    const context = useContext(Ctx);

    if (!context) {
        throw new ContextOutsideProviderError();
    }

    return context;
};

export const getUseContextOrThrowError = <T>(Ctx: Context<T | null>): () => T | never => () =>
    useCustomContext<T>(Ctx);
