import {createAsyncThunk, createSlice, isFulfilled} from "@reduxjs/toolkit";
import axios from "axios";
import {IFilterOptions} from "./interfaces/IFilterOption.ts";

interface IFilters {
    filterOptions: IFilterOptions[];
    allFieldNames: string[]
}

const initialState: IFilters = {
    filterOptions: [],
    allFieldNames: []
};

// Actions
const apiUrl = 'api/filters';

export const getFilterOptions = createAsyncThunk('getFilterOptions', async () => {
    const response = await axios.get<IFilterOptions[]>(apiUrl);
    return response.data
});

export const FilterSlice = createSlice({
    name: 'filter',
    initialState,
    reducers: {
        setFieldNames: (state, action) => {
            state.filterOptions = action.payload
        },
    },
    extraReducers(builder) {
        builder
            .addMatcher(isFulfilled(getFilterOptions), (state, action) => {
                state.filterOptions = action.payload;
                const test =  action.payload.map(item => {
                    return  item.fieldName;
                })
                state.allFieldNames = test
            })
    }
})

export const {setFieldNames} = FilterSlice.actions;

// Reducer
export default FilterSlice.reducer;


