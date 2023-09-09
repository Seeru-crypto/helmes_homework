import styled from "styled-components";
import React from "react";
import { Checkbox } from 'antd';
import type { CheckboxChangeEvent } from 'antd/es/checkbox';

interface CheckboxProps {
}


const CheckboxInput = (props: CheckboxProps) => {

    const onChange = (e: CheckboxChangeEvent) => {
        console.log(`checked = ${e.target.checked}`);
    };
    return (
        <CheckboxStyle>
            <Checkbox onChange={onChange}>Checkbox</Checkbox>
        </CheckboxStyle>
    )
}


export default CheckboxInput

const CheckboxStyle = styled.div`
width: auto;
`