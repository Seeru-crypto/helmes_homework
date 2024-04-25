import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {toast} from 'react-toastify';
import {toastDefaultSettings} from "../util/utils.ts";
import axios from "axios";

interface ISettings {
    count: number,
    isApiUp: boolean
}

const initialState: ISettings = {
    count: 0,
    isApiUp: false
};

const apiUrl = 'api/actuator/health';

type statusType = "UP" | "DOWN"

interface IServerStatus {
    status:statusType
}

export const getServerStatus = createAsyncThunk('getServerStatus', async () => {
    const response = await axios.get<IServerStatus>(apiUrl);
    return response.data
});

export const settingSlice = createSlice({
    name: 'settings',
    initialState,
    reducers: {
        increaseCount: (state) => {
            state.count = state.count + 1;
            toast.success("increase", toastDefaultSettings)
        }, decreaseCount: (state) => {
            state.count = state.count - 1;
            toast.warning("decrease", toastDefaultSettings)
        }
    },
    extraReducers(builder) {
        builder
            .addCase(getServerStatus.fulfilled, (state, action) => {
                console.log("here ", action.payload.status)
                if (action.payload.status === 'UP') state.isApiUp = true;
            })
    }
})

export const {increaseCount, decreaseCount} = settingSlice.actions;
export default settingSlice.reducer;