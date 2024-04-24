import { Checkbox } from 'antd';
import styles from "./CheckboxInput.module.scss"
import type { CheckboxChangeEvent } from 'antd/es/checkbox';

interface CheckboxProps {
    checkboxState: (boolean: boolean) => void
    label: string
}

const CheckboxInput = (props: CheckboxProps) => {

    const onChange = (e: CheckboxChangeEvent) => {
        props.checkboxState(e.target.checked)
    };

    return (
        <div className = {styles.container}>
            <Checkbox onChange={onChange}>{props.label}</Checkbox>
        </div>
    )
}


export default CheckboxInput
