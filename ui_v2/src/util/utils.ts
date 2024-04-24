import {MessageInstance} from "antd/es/message/interface";
import {SectorDto} from "../entities/interfaces/SectorDto.ts";
import {SaveUserDto} from "../entities/interfaces/SaveUserDto.ts";
import {ICascaderOptions} from "../components/user_registration_form/CascaderInput/CascaderInput.tsx";

export function mapToOptions(data: SectorDto[]): ICascaderOptions[] {
    if (!data) return ([] as ICascaderOptions[]);
    return data.map((sector: SectorDto) => {
        return {
            value: sector.name,
            label: sector.name,
            children: sector.children ? mapToOptions(sector.children) : []
        }
    })
}

export function isUserDataValid(dto: SaveUserDto, messageApi: MessageInstance ) {

    if (!dto.agreeToTerms){
        messageApi.error("You must agree to the terms and conditions");
        return false;
    }

    else if (dto.sectorIds.length === 0){
        messageApi.error("You must select at least one sector");

        return false;
    }

    else if (dto.name.length === 0){
        messageApi.error("You must enter a username");
        return false;
    }
    return true;
}

export function mapSectorsToIds(selectedSectors: string[][], sectors: SectorDto[]): (number | undefined)[] {
    return selectedSectors.map((sector: string[]) => {
        const lastElement = sector[sector.length - 1]
        return handleSearch(lastElement, sectors)
    })
}

const searchSector = (sectors: SectorDto[], searchTerm: string): number | null => {
    for (let i = 0; i < sectors.length; i++) {
        const sector = sectors[i];
        if (sector.name === searchTerm) {
            return sector.id;
        } else if (sector.children && sector.children.length > 0) {
            const result = searchSector(sector.children, searchTerm);
            if (result !== null) {
                return result;
            }
        }
    }
    return null;
};

export const handleSearch = (searchTerm: string, sectors: SectorDto[]): number | undefined => {
    console.log(`Searching for sector '${searchTerm}'...`)
    const id = searchSector(sectors, searchTerm);
    if (id !== null) {
        console.log(`ID of sector '${searchTerm}': ${id}`);
        return id;
    } else {
        console.log(`Sector '${searchTerm}' not found.`);
    }
}