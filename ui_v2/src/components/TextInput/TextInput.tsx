import styles from "./TextInput.module.scss";
import {Input} from 'antd';

interface TextInputProps {
    placeholder?: string
    onChange: (e: string) => void
    value: string
}

const TextInput = (props: TextInputProps) => {
    return (
        <Input
            className={styles.input}
            placeholder={props.placeholder}
            onChange={(e) => props.onChange(e.target.value)}
            value={props.value}
        />
    )
}

export default TextInput
