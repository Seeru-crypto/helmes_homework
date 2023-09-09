import styled from "styled-components";
import React from "react";
import {Cascader} from 'antd';

interface CascaderProps {
    options: CascaderOptionProps[]
    selectedSectorsCallback: (value: string[][]) => void
}

export interface CascaderOptionProps {
    value: string | number;
    label: string;
    children?: CascaderOptionProps[];
    disableCheckbox?: boolean;
}

const CascaderInput = (props: CascaderProps) => {
    const onChange = (value: string[][]) => {
        props.selectedSectorsCallback(value)
    };

    return (
        <CascaderStyle>
            <Cascader
                style={{width: '100%'}}
                options={props.options}
                onChange={onChange}
                showSearch={true}
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