import axios from 'axios';
import {createAsyncThunk, createSlice, isRejected} from '@reduxjs/toolkit';
import {IUser} from "./interfaces/IUser.ts";
import {IPageableWrapper} from "./interfaces/IPageableWrapper.ts";
import {ISaveUser} from "./interfaces/ISaveUser.ts";
import {toast} from "react-toastify";

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
    const fullPAth = "http://localhost:8880/" + apiUrl + pathParams
    const response = await axios.get<IPageableWrapper<IUser>>(fullPAth);
    return response.data
});

export const saveUser = createAsyncThunk('saveUser', async (entity: ISaveUser, thunkApi) => {
    const path = "http://localhost:8880/" + apiUrl
    const response = await axios.post<ISaveUser>(path, entity);
    void thunkApi.dispatch(getUsers());

    return response.data
});

export const UserSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addCase(saveUser.fulfilled, () => {
                toast("user saved")
            })
            .addMatcher(isRejected(saveUser), (state, action) => {
                const test = action.error.message
                toast(test)
            })
    }
})

export const {} = UserSlice.actions;

// Reducer
export default UserSlice.reducer;
