export interface ISector {
  name: string,
  id: number,
  parentId: number,
  value: number,
  children: ISector[]
}

export const defaultValue: Readonly<ISector> = {
  name: "string",
  id: 0,
  parentId: 0,
  value: 0,
  children: []
};
