import styled from "styled-components";
import React from "react";
import {Cascader} from 'antd';

interface CascaderProps {
    options: Option[]
}

export interface Option {
    value: string | number;
    label: string;
    children?: Option[];
    disableCheckbox?: boolean;
}

const CascaderInput = (props: CascaderProps) => {
    const onChange = (value: string[][]) => {
        console.log(value);
    };

    return (
        <CascaderStyle>
            <Cascader
                style={{ width: '100%' }}
                options={props.options}
                onChange={onChange}
                multiple
                maxTagCount="responsive"
            />
        </CascaderStyle>
    )
}


export default CascaderInput

const CascaderStyle = styled.div`
width: 100%;
`