import {configureStore} from '@reduxjs/toolkit';
import {TypedUseSelectorHook, useDispatch, useSelector} from 'react-redux';
import reducers from "./reducers.ts";

export const store = configureStore({
  reducer: reducers,
});

export type AppDispatch = typeof store.dispatch;
export const useAppDispatch = (): AppDispatch => useDispatch<AppDispatch>();
export type IRootState = ReturnType<typeof store.getState>;
export const useAppSelector: TypedUseSelectorHook<IRootState> = useSelector;
