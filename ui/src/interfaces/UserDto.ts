import {SectorDto} from "./SectorDto";

export interface UserDto {
    name: string;
    sectors: SectorDto[];
    agreeToTerms: boolean;
}