import {createSlice} from "@reduxjs/toolkit";

interface ISettings {
    count: number
}

const initialState: ISettings = {
    count : 0,
};


export const settingSlice = createSlice({
    name: 'settings',
    initialState,
    reducers: {
        increaseCount: (state) => {
            console.log("increase")
            state.count = state.count + 1;

        },
    },
})

export const { increaseCount } = settingSlice.actions;
export default settingSlice.reducer;