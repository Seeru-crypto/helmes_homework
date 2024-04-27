import {Card, Tag} from "antd";
import {CardSize} from "antd/es/card/Card";

interface UserCardProps {
    size?: CardSize;
    title: string;
    sectors: string[] | undefined;
}

const UserCard = (props: UserCardProps) => {
    return (
            <Card size="small" title={props.title} style={{width: 300}}>
                { props.sectors?.map((sector: string, index) => {
                            return (
                                <Tag key={index} color="geekblue">{sector}</Tag>
                            )
                        }
                    )
                }
            </Card>
    )
}
export default UserCard