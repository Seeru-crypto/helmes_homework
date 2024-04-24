import {createSlice} from "@reduxjs/toolkit";
import {toast} from 'react-toastify';

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
            toast("increase")

        }, decreaseCount: (state) => {
            state.count = state.count - 1;
            toast("decrease")
        }
    },
})

export const {increaseCount, decreaseCount} = settingSlice.actions;
export default settingSlice.reducer;