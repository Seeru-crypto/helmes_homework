import axios from 'axios';
import {createAsyncThunk, createSlice, isFulfilled} from '@reduxjs/toolkit';
import {IUser} from "./interfaces/IUser.ts";
import {IPageableWrapper} from "./interfaces/IPageableWrapper.ts";

interface Iusers {
    users: IUser[];
}

const initialState: Iusers = {
    users: []
};

const apiUrl = 'api/users';

// Actions

export const getUsers = createAsyncThunk('getUsers', async () => {
    const pathParams = `?sort=id&page=0&size=10`
    const fullPAth = "http://localhost:8880/" + apiUrl+ pathParams
    const response = await axios.get<IPageableWrapper<IUser>>(fullPAth);
    return response.data
});

export const UserSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addMatcher(isFulfilled(getUsers), (state, action) => {
                state.users = action.payload.content;
            })
    }
})

export const {} = UserSlice.actions;

// Reducer
export default UserSlice.reducer;
