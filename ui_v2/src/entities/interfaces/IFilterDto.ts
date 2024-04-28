import {AllowedValies} from "./IFilterOption.ts";

export interface IFilterDto {
  type: AllowedValies,
  criteria: string,
  value: string
  fieldName: string
}

