import {Select} from "antd";

export interface ISelector {
    options: ISelectorOptions[],
    onChange: (arg0: string) => void
    defaultValue: string
}

export interface ISelectorOptions {
    value: string,
    label: string
}

export function Selector (props: ISelector) {
    return (
        <Select
            showSearch
            defaultValue={props.defaultValue}
            style={{width: '40%'}}
            onChange={(e) => props.onChange(e)}
            options={props.options}
        />
    )
}

export default Selector