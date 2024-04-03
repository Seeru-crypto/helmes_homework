export interface SectorDto {
    name: string
    id: number
    children: SectorDto[]
}