import styled from "styled-components";
import {GetServerSideProps} from "next";
import {ReactElement, useEffect, useState} from "react";
import CascaderInput, {CascaderOptionProps} from "../components/CascaderInput";
import CheckboxInput from "../components/CheckboxInput";
import TextInput from "../components/TextInput";
import CustomButton from "../components/Button";
import {GetRequest, PostRequest, PutRequest} from "../controller/ApiServices";
import {SectorDto} from "../interfaces/SectorDto";
import {isUserDataValid, mapSectorsToIds, mapToOptions} from "../utils";
import {SaveUserDto} from "../interfaces/SaveUserDto";
import {message} from 'antd';
import {useMessageStore} from "../zustand/store";
import {SlugSector, SlugUsers} from "../configs";
import UserCard from "../components/UserCard";
import {UserDto} from "../interfaces/UserDto";

interface LandingProps {
    sectors: SectorDto[];
    existingUsers: UserProps;
}

interface UserProps extends PaginationWrapper {
    content: UserDto[]
}

export interface PaginationWrapper {
    totalPages: number
    totalElements: number
    size: number
    numberOfElements: number
    first: boolean
    last: boolean
    empty: boolean
}

export default function Home({sectors, existingUsers}: LandingProps): ReactElement | null {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const [sectorOptions, setSectorOptions] = useState<CascaderOptionProps[]>([])
    const [users, setUsers] = useState<UserDto[]>([])
    const [messageApi, contextHolder] = message.useMessage();
    const {setMessageApi} = useMessageStore();

    useEffect(() => {
        setSectorOptions(mapToOptions(sectors))
        sessionStorage.clear();
    }, [sectors])

    useEffect(() => {
        setUsers(existingUsers.content)
    }, [existingUsers])

    useEffect(() => {
        setMessageApi(messageApi)
    }, [messageApi])

    async function submit() {
        const dto: SaveUserDto = {
            name: username,
            sectorIds: mapSectorsToIds(selectedSectors, sectors),
            agreeToTerms: agreeToTerms
        }

        if (!isUserDataValid(dto, messageApi)) return

        const userId = sessionStorage.getItem("USER_ID");
        if (userId === null) {
            await PostRequest(SlugUsers, dto, messageApi, "user created successfully").then(async (res) => {
                sessionStorage.setItem("USER_ID", res.id.toString());
                const updatedUSers: UserProps = await GetRequest(SlugUsers, 'outer')
                setUsers(updatedUSers.content)
            })

        } else {
            const slug = SlugUsers + "/" + userId
            await PutRequest(slug, dto, messageApi, "user updated successfully")
            const updatedUSers: UserProps = await GetRequest(SlugUsers, 'outer')
            setUsers(updatedUSers.content)
        }
    }

    return (
        <HomeStyle>
            {contextHolder}
            <div className="registration">
                <div className="left">
                    <TextInput placeholder="username" onChange={setUsername}/>
                    <CheckboxInput label="agree to terms"
                                   checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)}/>
                </div>
                <div className="right">
                    <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={sectorOptions}/>
                    <CustomButton label="submit" onClick={() => submit()}/>
                </div>
            </div>
            <div className="users">
                <h2>Users</h2>
                <div className="user-card-list">
                    {
                        users.length > 0 ?
                            users.map((user: UserDto) => {
                                    return (
                                        <UserCard key={user.name} title={user.name} size={"small"} sectors={user.sectorNames}/>
                                    )
                                }
                            ) : <p>no users</p>
                    }
                </div>
            </div>
        </HomeStyle>
    )
}

const HomeStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 8rem;

  .registration {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 4rem;
    width: 100%;
    margin-bottom: 4rem;
  }

  .left, .right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 4rem;
    width: 100%;
  }

  .user-card-list {
    display: flex;
    flex-direction: row;
    gap: 4rem;
    flex-wrap: wrap;
  }
  
  .users {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
`

export const getServerSideProps: GetServerSideProps = async () => {
    const sectors: SectorDto[] = await GetRequest(SlugSector, 'inner' )
    const existingUsers: UserProps = await GetRequest(SlugUsers, 'inner')
    return {props: {sectors, existingUsers}};
};