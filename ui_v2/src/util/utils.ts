import {MessageInstance} from "antd/es/message/interface";
import {ISaveUser} from "../entities/interfaces/ISaveUser.ts";
import {ICascaderOptions} from "../views/users/userRegistration/CascaderInput/CascaderInput.tsx";
import {ISector} from "../entities/interfaces/ISector.ts";
import {Bounce, ToastOptions} from "react-toastify";
import {IPage} from "../entities/interfaces/IPage.ts";

export function mapToOptions(data: ISector[]): ICascaderOptions[] {
    if (!data) return ([] as ICascaderOptions[]);
    return data.map((sector: ISector) => {
        return {
            value: sector.name,
            label: sector.name,
            children: sector.children ? mapToOptions(sector.children) : []
        }
    })
}

export const toastDefaultSettings: ToastOptions<unknown> =  {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
}

export function isUserDataValid(dto: ISaveUser, messageApi: MessageInstance ) {

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

export function mapSectorsToIds(selectedSectors: string[][], sectors: ISector[]): number[] {
    return selectedSectors
        .map((sector: string[]) => {
        const lastElement = sector[sector.length - 1]
        return handleSearch(lastElement, sectors)
    }) as number[];
}

const searchSector = (sectors: ISector[], searchTerm: string): number | null => {
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

export function getPathParams(entity: IPage): string {
    return `?sort=${entity.sort}&page=${entity.page}&size=${entity.size}`
}

export const handleSearch = (searchTerm: string, sectors: ISector[]): number | undefined => {
    const id = searchSector(sectors, searchTerm);
    if (id !== null) {
        return id;
    } else {
        console.log(`Sector '${searchTerm}' not found.`);
    }
}
