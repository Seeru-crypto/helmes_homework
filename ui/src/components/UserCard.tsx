import styled from "styled-components";
import {Card, Tag} from "antd";
import {CardSize} from "antd/es/card/Card";


interface UserCardProps {
    size?: CardSize;
    title: string;
    sectors: string[];
}

const UserCard = (props: UserCardProps) => {
    return (
        <UserCardStyle>
            <Card size="small" title={props.title} style={{width: 300}}>
                { props.sectors.length > 0 &&
                    props.sectors.map((sector: string, index) => {
                        return (
                            <Tag key={index} color="geekblue">{sector}</Tag>
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