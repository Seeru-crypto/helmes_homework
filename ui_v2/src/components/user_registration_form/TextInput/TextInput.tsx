import styles from "./TextInput.module.scss";
import { Input } from 'antd';

interface TextInputProps {
    placeholder: string
    onChange: (e: string) => void
}

const TextInput = (props: TextInputProps) => {
    return (
        <div className={styles.container}>
            <Input className={styles.input} placeholder={props.placeholder} onChange={(e) => props.onChange(e.target.value)} />
        </div>
    )
}

export default TextInput
