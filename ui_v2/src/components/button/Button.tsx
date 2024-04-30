import styles from "./button.module.scss"


type TVariant= "mainButton" | "removeButton"

interface ICustomButton {
    onClick: (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
    children: React.ReactNode
    isDisabled?:boolean
    variant?: TVariant
}

const CustomButton: React.FC<ICustomButton> = ({ onClick, children, isDisabled, variant }) => {
    function getClassName(): string {
        switch (variant) {
            case "mainButton":
                return styles.button
            case "removeButton":
                return styles.removeButton
            default:
                return styles.button
        }
    }

    return (
        <button disabled={isDisabled} className={getClassName()} onClick={onClick}>
            {children}
        </button>
    );
};
export default CustomButton;