import axios from 'axios';
import {createAsyncThunk, isFulfilled, isPending} from '@reduxjs/toolkit';
import {createEntitySlice, EntityState, IQueryParams} from "../../store/reducer.utils.ts";
import {ASC} from "../../util/pagination.constants.ts";
import {defaultValue, IUser} from "../../store/user.model.ts";

const initialState: EntityState<IUser> = {
    loading: false,
    errorMessage: null,
    entities: [],
    entity: defaultValue,
    updating: false,
    updateSuccess: false,
};

const apiUrl = 'api/regions';

// Actions

export const getEntities = createAsyncThunk('region/fetch_entity_list', async ({ sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<IUser[]>(requestUrl);
});


// slice

export const UserSlice = createEntitySlice({
    name: 'user',
    initialState,
    extraReducers(builder) {
        builder
            .addMatcher(isFulfilled(getEntities), (state, action) => {
                const { data } = action.payload;

                return {
                    ...state,
                    loading: false,
                    entities: data.sort((a, b) => {
                        if (!action.meta?.arg?.sort) {
                            return 1;
                        }
                        const order = action.meta.arg.sort.split(',')[1];
                        const predicate = action.meta.arg.sort.split(',')[0];
                        return order === ASC ? (a[predicate] < b[predicate] ? -1 : 1) : b[predicate] < a[predicate] ? -1 : 1;
                    }),
                };
            })
            .addMatcher(isPending(getEntities), state => {
                state.errorMessage = null;
                state.updateSuccess = false;
                state.loading = true;
            })
    },
});

export const { reset } = UserSlice.actions;

// Reducer
export default UserSlice.reducer;
