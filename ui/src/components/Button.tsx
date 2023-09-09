import { Button } from 'antd';
import styled from "styled-components";

interface ButtonProps {

}

const CustomButton = (props: ButtonProps) => {
    return (
        <ButtonStyle>
            <Button type="primary">submit</Button>
        </ButtonStyle>
    )
}
export default CustomButton;

const ButtonStyle = styled.div``
