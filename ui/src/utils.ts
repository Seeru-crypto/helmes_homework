import {SectorDto} from "./interfaces/SectorDto";
import {CascaderOptionProps} from "./components/CascaderInput";
import {UserDto} from "./interfaces/UserDto";

export function mapToOptions(data: SectorDto[]): CascaderOptionProps[] {
    return data.map((sector: SectorDto) => {
        return {
            value: sector.name,
            label: sector.name,
            children: sector.children ? mapToOptions(sector.children) : []
        }
    })
}


export function validateUserData(dto: UserDto) {
    if (!dto.agreeToTerms){
        // Call toast here
        return false;
    }

    else if (dto.selectedSectors.length === 0){
        // Call toast here
        return false;
    }

    else if (dto.username.length === 0){
        // Call toast here
        return false;
    }

    return true;
}