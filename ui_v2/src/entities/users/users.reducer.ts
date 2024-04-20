import axios from 'axios';
import {createAsyncThunk, createSlice, isFulfilled} from '@reduxjs/toolkit';
import {IQueryParams} from "../../store/reducer.utils.ts";
import {IUser} from "../../store/user.model.ts";


interface Iusers {
    users: IUser[];
}


const initialState: Iusers = {
    users: []
};

const apiUrl = 'api/regions';

// Actions

export const getEntities = createAsyncThunk('region/fetch_entity_list', async ({sort}: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<IUser[]>(requestUrl);
});


// slice

export const UserSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addMatcher(isFulfilled(getEntities), (state, action) => {
                state.users = action.payload.data;
            })
    }
})

export const {} = UserSlice.actions;

// Reducer
export default UserSlice.reducer;
