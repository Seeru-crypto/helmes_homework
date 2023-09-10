import styled from "styled-components";
import {Card} from "antd";
import {CardSize} from "antd/es/card/Card";
import {SectorDto} from "../interfaces/SectorDto";


interface UserCardProps {
    size?: CardSize;
    title: string;
    sectors: SectorDto[];
}

const UserCard = (props: UserCardProps) => {
    function formatSectorsToStringArray(sectors: SectorDto[]): string[] {
        return sectors.map(sector => sector.name)
    }

    return (
        <UserCardStyle>
            <Card size="small" title={props.title} style={{width: 300}}>
                <p> { formatSectorsToStringArray(props.sectors).join(', ') }  </p>
            </Card>
        </UserCardStyle>
    )
}
export default UserCard

const UserCardStyle = styled.div``