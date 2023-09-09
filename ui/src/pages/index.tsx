import styled from "styled-components";
import {GetServerSideProps} from "next";
import {ReactElement, useEffect, useState} from "react";
import CascaderInput, {Option} from "../components/CascaderInput";
import CheckboxInput from "../components/CheckboxInput";
import TextInput from "../components/TextInput";
import CustomButton from "../components/Button";

interface LandingProps {
    filterHistory: FilterDto;
    toggleTheme: () => void;
}

export interface FilterDto {
    totalPages: number
    totalElements: number
    size: number
    numberOfElements: number
    first: boolean
    last: boolean
    empty: boolean
}

export default function Home({filterHistory}: LandingProps): ReactElement | null  {
    const [username, setUsername] = useState('')



    useEffect(() => {
        console.log({username})
    }, [username])


    const options: Option[] = [
        {
            label: 'Light',
            value: 'light',
            children: new Array(20)
                .fill(null)
                .map((_, index) => ({ label: `Number ${index}`, value: index })),
        },
        {
            label: 'Bamboo',
            value: 'bamboo',
            children: [
                {
                    label: 'Little',
                    value: 'little',
                    children: [
                        {
                            label: 'Toy Fish',
                            value: 'fish',
                            disableCheckbox: true,
                        },
                        {
                            label: 'Toy Cards',
                            value: 'cards',
                        },
                        {
                            label: 'Toy Bird',
                            value: 'bird',
                        },
                    ],
                },
            ],
        },
    ];

    return (
        <HomeStyle>
            <div className="registration">
                <div className="left">
                    <TextInput placeholder="username" onChange={setUsername} />
                    <CheckboxInput />
                </div>
                <div className="right">
                    <CascaderInput options={options} />
                    <CustomButton />
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
    // Fetch initial data
    return {props: {}};
};


