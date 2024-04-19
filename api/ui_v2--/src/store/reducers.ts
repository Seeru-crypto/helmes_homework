import { ReducersMapObject } from '@reduxjs/toolkit';

import locale from './locale';
import entitiesReducers from "./entitiesReducers.ts";

/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer: ReducersMapObject = {
    locale,
    /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
    ...entitiesReducers,
};

export default rootReducer;
