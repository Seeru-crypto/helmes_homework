export interface SectorDto {
    id: number
    name: string
    parentId: number
    children: SectorDto[]
}