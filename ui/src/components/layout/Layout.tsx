import Navbar from './Navbar'
import styled from "styled-components";
import React from "react";
export default function Layout({children} : {children: React.ReactNode} ) {
    return (
        <LayoutStyle>
            <Navbar/>
            <main>{children}</main>
        </LayoutStyle>
    )
}

const LayoutStyle = styled.div`
  min-height: 100vh;
  margin: 0;
  padding: 0;
  background-color: ${({ theme }) => theme.background};
  color: ${({ theme }) => theme.primaryColor};
  font-family: 'Poppins', sans-serif;
`
