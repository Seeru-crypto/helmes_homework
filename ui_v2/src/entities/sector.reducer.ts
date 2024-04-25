import {ISector} from "./interfaces/ISector.ts";
import {createAsyncThunk, createSlice, isFulfilled} from "@reduxjs/toolkit";
import axios from "axios";

interface ISectors {
    sectors: ISector[];
}

const initialState: ISectors = {
    sectors: []
};

// Actions
const apiUrl = 'api/sectors';
export const getSectors = createAsyncThunk('get_sectors', async () => {
    const response = await axios.get<ISector[]>(apiUrl);
    return response.data
});

export const SectorSlice = createSlice({
    name: 'sector',
    initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addMatcher(isFulfilled(getSectors), (state, action) => {
                state.sectors = action.payload;
            })
    }
})

export const {} = SectorSlice.actions;

// Reducer
export default SectorSlice.reducer;


