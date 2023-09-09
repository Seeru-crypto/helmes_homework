import styled from "styled-components";
import {GetServerSideProps} from "next";
import {ReactElement, useEffect, useState} from "react";
import CascaderInput, {CascaderOptionProps} from "../components/CascaderInput";
import CheckboxInput from "../components/CheckboxInput";
import TextInput from "../components/TextInput";
import CustomButton from "../components/Button";
import {GetRequest, PostRequest} from "../controller/ApiServices";
import {SectorDto} from "../interfaces/SectorDto";
import {mapToOptions, validateUserData} from "../utils";
import {UserDto} from "../interfaces/UserDto";

interface LandingProps {
    sectors: SectorDto[];
}

interface SectorProps extends PaginationWrapper {
    content: SectorDto[]
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

export default function Home({sectors}: LandingProps): ReactElement | null  {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const [options, setOptions] = useState<CascaderOptionProps[]>([])

    useEffect(() => {
        setOptions(mapToOptions(sectors))
    }, [sectors])


    async function submit() {
        const dto: UserDto = {
            username: username,
            selectedSectors: selectedSectors[0],
            agreeToTerms: agreeToTerms
        }

        if (validateUserData(dto)){
            const res = await PostRequest('user', dto);
            console.log("res ", res);
        }


    }

    return (
        <HomeStyle>
            <div className="registration">
                <div className="left">
                    <TextInput placeholder="username" onChange={setUsername} />
                    <CheckboxInput label="agree to terms" checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)  } />
                </div>
                <div className="right">
                    <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={options} />
                    <CustomButton label="submit" onClick={() => submit()} />
                </div>
            </div>
            <div className="users">
                <h2>Users</h2>
            </div>
        </HomeStyle>
    )
}

const HomeStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: ${({ theme }) => theme.size.$100};;
  gap:  ${({ theme }) => theme.size.$100};
  width: 80%;
  
  .registration {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    gap: 4rem;
    width: 100%;
  }
  
  .left, .right {
    display: flex;
    flex-direction: column;
    gap: 4rem;
    width: 100%;
  }
  
`

export const getServerSideProps: GetServerSideProps = async () => {
    const sectors: SectorDto[] = await GetRequest('sectors')
    return {props: {sectors}};
};


