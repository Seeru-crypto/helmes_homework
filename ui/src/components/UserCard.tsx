import styled from "styled-components";
import {Card, Tag} from "antd";
import {CardSize} from "antd/es/card/Card";
import {SectorDto} from "../interfaces/SectorDto";


interface UserCardProps {
    size?: CardSize;
    title: string;
    sectors: SectorDto[];
}

const UserCard = (props: UserCardProps) => {
    return (
        <UserCardStyle>
            <Card size="small" title={props.title} style={{width: 300}}>
                {
                    props.sectors.map((sector: SectorDto, index) => {
                        return (
                            <Tag key={index} color="geekblue">{sector.name}</Tag>
                        )
                    }
                    )
                }
            </Card>
        </UserCardStyle>
    )
}
export default UserCard

const UserCardStyle = styled.div`

`