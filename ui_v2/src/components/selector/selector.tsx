import {Select} from "antd";
import styles from "./selector.module.scss"

export interface ISelector {
    options: ISelectorOptions[],
    onChange: (arg0: string) => void
    value: string,
    isDisabled?: boolean
}

export interface ISelectorOptions {
    value: string,
    label: string
}

export function Selector (props: ISelector) {
    return (
            <Select
                disabled={props.isDisabled}
                className={styles.container}
                showSearch
                value={props.value}
                onChange={(e) => props.onChange(e)}
                options={props.options}
            />

    )
}

export default Selector