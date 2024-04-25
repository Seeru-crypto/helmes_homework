import axios from 'axios';
import {createAsyncThunk, createSlice, isFulfilled, isRejected} from '@reduxjs/toolkit';
import {IUser} from "./interfaces/IUser.ts";
import {IPageableWrapper} from "./interfaces/IPageableWrapper.ts";
import {ISaveUser} from "./interfaces/ISaveUser.ts";
import {toast} from "react-toastify";
import {getPathParams, toastDefaultSettings} from "../util/utils.ts";
import {IPage} from "./interfaces/IPage.ts";

interface Iusers {
    users: IUser[];
    currentUserID: string;
    totalUsers: number
}

const initialState: Iusers = {
    users: [],
    currentUserID: "",
    totalUsers: 0
};

const apiUrl = 'api/users';

// Actions

export const getUsers = createAsyncThunk('getUsers', async (page: IPage) => {
    const pathParams = getPathParams(page)
    const fullPAth = apiUrl + pathParams
    const response = await axios.get<IPageableWrapper<IUser>>(fullPAth, );
    return response.data
});

export const saveUser = createAsyncThunk('saveUser', async (entity: ISaveUser, thunkApi) => {
    const response = await axios.post<IUser>(apiUrl, entity);
    void thunkApi.dispatch(getUsers(defaultPage));
    return response.data
});

interface IUpdateUser {
    payload: ISaveUser,
    userId: string
}

export const updateUser = createAsyncThunk('updateUser', async (entity: IUpdateUser, thunkApi) => {
    const response = await axios.put<IUser>(`${apiUrl}/${entity.userId}`, entity.payload);
    void thunkApi.dispatch(getUsers(defaultPage));
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
                state.currentUserID = action.payload.id ?? ""
            })
            .addCase(updateUser.fulfilled, () => {
                toast.success("user updated", toastDefaultSettings)
            })
            .addMatcher(isFulfilled(getUsers), (state, action) => {
                state.users = action.payload.content;
                state.totalUsers = action.payload.totalElements
            })
            .addMatcher(isRejected(saveUser, updateUser), (_state, action) => {
                const test = action.error.message
                toast.error(test, {...toastDefaultSettings, autoClose:6000 })
            })
    }
})

const defaultPage: IPage = {
    sort: "createdAt,desc",
    size: 3,
    page: 0
}

// Reducer
export default UserSlice.reducer;
