import { Button } from 'antd';

interface ButtonProps {
    onClick: () => void
    label: string
}

const CustomButton = (props: ButtonProps) => {
    return (
        <div>
            <Button onClick={() => props.onClick()} type="primary">{props.label}</Button>
        </div>
    )
}
export default CustomButton;

