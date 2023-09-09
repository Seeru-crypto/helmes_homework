import styled from "styled-components";
import {GetServerSideProps} from "next";
import {ReactElement, useEffect} from "react";

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

    return (
        <HomeStyle>
            <div>
                Hello world
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
`

export const getServerSideProps: GetServerSideProps = async () => {
    // Fetch initial data
    return {props: {}};
};


