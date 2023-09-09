import styled from "styled-components";
import React from "react";
import { Checkbox } from 'antd';
import type { CheckboxChangeEvent } from 'antd/es/checkbox';

interface CheckboxProps {
    checkboxState: (boolean) => void
    label: string
}

const CheckboxInput = (props: CheckboxProps) => {

    const onChange = (e: CheckboxChangeEvent) => {
        props.checkboxState(e.target.checked)
    };

    return (
        <CheckboxStyle>
            <Checkbox onChange={onChange}>{props.label}</Checkbox>
        </CheckboxStyle>
    )
}


export default CheckboxInput

const CheckboxStyle = styled.div`
width: auto;
`