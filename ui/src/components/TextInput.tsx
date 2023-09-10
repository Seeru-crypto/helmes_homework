import styled from "styled-components";
import { Input } from 'antd';

interface TextInputProps {
    placeholder: string
    onChange: (e: string) => void
}

const TextInput = (props: TextInputProps) => {
    return (
        <TextInputStyle>
            <Input placeholder={props.placeholder} onChange={(e) => props.onChange(e.target.value)} />
        </TextInputStyle>
    )
}

export default TextInput

const TextInputStyle = styled.div`
width: 50%`
