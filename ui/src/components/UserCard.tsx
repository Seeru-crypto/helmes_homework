import styled from "styled-components";
import {Card, Tag} from "antd";
import {CardSize} from "antd/es/card/Card";
import {useEffect, useState} from "react";

interface UserCardProps {
    size?: CardSize;
    title: string;
    sectors: string[];
}

const UserCard = (props: UserCardProps) => {
    const [title, setTitle] = useState("")
    const [sectors, setSectors] = useState<string[]>([])

    useEffect(() => {
        if (title !== undefined) setTitle(props.title)
        else {
            console.warn("title undefined")
        }
        if (props.sectors !== undefined && props.sectors.length > 0) {
            setSectors(props.sectors)
        }
        else {
            console.warn("sectors undefined")
        }
    }, [props])

    return (
        <UserCardStyle>
            <Card size="small" title={title} style={{width: 300}}>
                { sectors.map((sector: string, index) => {
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