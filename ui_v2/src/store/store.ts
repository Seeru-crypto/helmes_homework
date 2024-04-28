import {combineReducers, configureStore} from '@reduxjs/toolkit';
import {TypedUseSelectorHook, useDispatch, useSelector} from 'react-redux';
import usersReducer from "../entities/users.reducer.ts";
import settingReducer from "../entities/setting.reducer.ts";
import sectorReducer from "../entities/sector.reducer.ts";
import filtersReducer from "../entities/filters.reducer.ts";

const rootReducer = combineReducers({
  user: usersReducer,
  setting: settingReducer,
  sectors: sectorReducer,
  filters: filtersReducer
})

export const store = configureStore({
  reducer: rootReducer,
});

export type AppDispatch = typeof store.dispatch;
export const useAppDispatch = (): AppDispatch => useDispatch<AppDispatch>();
export type IRootState = ReturnType<typeof store.getState>;
export const useAppSelector: TypedUseSelectorHook<IRootState> = useSelector;
