import {createSlice} from "@reduxjs/toolkit";
import {Slide, toast} from 'react-toastify';
import {toastDefaultSettings} from "../util/utils.ts";

interface ISettings {
    count: number,
}

const initialState: ISettings = {
    count: 0,
};

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
})

export const {increaseCount, decreaseCount} = settingSlice.actions;
export default settingSlice.reducer;