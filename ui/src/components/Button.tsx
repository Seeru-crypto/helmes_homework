import { Button } from 'antd';
import styled from "styled-components";

interface ButtonProps {
    onClick: () => void
    label: string
}

const CustomButton = (props: ButtonProps) => {
    return (
        <ButtonStyle>
            <Button onClick={() => props.onClick()} type="primary">{props.label}</Button>
        </ButtonStyle>
    )
}
export default CustomButton;

const ButtonStyle = styled.div``
