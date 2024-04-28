import styles from "./button.module.scss"

interface ButtonProps {
    onClick: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
    children: React.ReactNode
}

const CustomButton: React.FC<ButtonProps> = ({ onClick, children }) => {
    return (
        <button className={styles.button} onClick={onClick}>
            {children}
        </button>
    );
};
export default CustomButton;