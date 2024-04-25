import styles from "./button.module.scss"
interface ButtonProps {
    onClick: () => void
    label: string
}

const CustomButton = (props: ButtonProps) => {
    return (
            <button className={styles.button} onClick={() => props.onClick()}>{props.label}</button>
    )
}
export default CustomButton;

