import {SectorDto} from "./SectorDto";

export interface UserDto {
    id: number;
    name: string;
    sectorIds: number[];
    agreeToTerms: boolean;
}