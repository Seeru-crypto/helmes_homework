import axios from 'axios';
import {createAsyncThunk, createSlice, isFulfilled, isRejected} from '@reduxjs/toolkit';
import {IUser} from "./interfaces/IUser.ts";
import {IPageableWrapper} from "./interfaces/IPageableWrapper.ts";
import {ISaveUser} from "./interfaces/ISaveUser.ts";
import {toast} from "react-toastify";
import {toastDefaultSettings} from "../util/utils.ts";

interface Iusers {
    users: IUser[];
    currentUserID: string;
}

const initialState: Iusers = {
    users: [],
    currentUserID: ""
};

const apiUrl = 'api/users';

// Actions

export const getUsers = createAsyncThunk('getUsers', async () => {
    const pathParams = `?sort=id&page=0&size=10`
    const fullPAth = apiUrl + pathParams
    const response = await axios.get<IPageableWrapper<IUser>>(fullPAth);
    console.log({response})
    return response.data
});

export const saveUser = createAsyncThunk('saveUser', async (entity: ISaveUser, thunkApi) => {
    const response = await axios.post<IUser>(apiUrl, entity);
    void thunkApi.dispatch(getUsers());
    return response.data
});

export const UserSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addCase(saveUser.fulfilled, (state, action) => {
                toast.success("user saved", toastDefaultSettings)
                state.currentUserID = action.payload.id || ""
            })
            .addMatcher(isFulfilled(getUsers), (state, action) => {
                state.users = action.payload.content;
            })
            .addMatcher(isRejected(saveUser), (_state, action) => {
                const test = action.error.message
                toast.error(test, {...toastDefaultSettings, autoClose:6000 })
            })
    }
})

// Reducer
export default UserSlice.reducer;
