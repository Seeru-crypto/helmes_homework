import {SectorDto} from "./interfaces/SectorDto";
import {CascaderOptionProps} from "./components/CascaderInput";
import {SaveUserDto} from "./interfaces/SaveUserDto";
import {MessageInstance} from "antd/es/message/interface";

export function mapToOptions(data: SectorDto[]): CascaderOptionProps[] {
    if (!data) return ([] as CascaderOptionProps[]);
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

    else if (dto.sectors.length === 0){
        messageApi.error("You must select at least one sector");

        return false;
    }

    else if (dto.name.length === 0){
        messageApi.error("You must enter a username");
        return false;
    }

    return true;
}