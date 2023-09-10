import {SectorDto} from "./SectorDto";

export interface UserDto {
    id: number;
    name: string;
    sectors: SectorDto[];
    agreeToTerms: boolean;
}