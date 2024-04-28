export interface IFilterOptions {
  fieldName: string,
  allowedValue: AllowedValies,
  criteria: string[]
}

export type AllowedValies = 'STRING' | 'DATE' | 'NUMBER'
