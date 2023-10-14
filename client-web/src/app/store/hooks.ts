import { type TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";

import { type RootState, type AppDispatch } from ".";

export const useAppDispatch: typeof useDispatch<AppDispatch> = () => useDispatch<AppDispatch>();

export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
