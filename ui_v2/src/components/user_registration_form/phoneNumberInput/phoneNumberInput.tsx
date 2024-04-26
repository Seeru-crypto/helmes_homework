import styles from "./phoneNumberInput.module.scss"
import {Input, Select, Space} from "antd";
import {useEffect, useState} from "react";

export interface IPhoneNumberInput {
    onChange: (arg0: IPhoneNumberPayload) => void
    defaultValues: IPhoneNumberPayload
}

export interface IPhoneNumberPayload {
    prefix: string,
    mainBody: string
}


export function PhoneNumberInput(props: IPhoneNumberInput) {
    const [prefix, setPrefix] = useState(props.defaultValues.prefix);
    const [mainBody, setMainBody] = useState(props.defaultValues.mainBody);

    const prefixes = [
        {
            value: "+372", label: "+372"
        },
        {
            value: "+1 ", label: "+1 ",
        },
        {
            value: "+44", label: "+44",

        }, {
            value: "+86", label: "+86",

        }, {
            value: "+81", label: "+81",

        }, {
            value: "+91", label: "+91",
        }, {

            value: "+61", label: "+61",
        }, {

            value: "+33", label: "+33",
        }, {
            value: "+49", label: "+49",
        }
    ]


    useEffect(() => {
        const payload: IPhoneNumberPayload = {
            prefix,
            mainBody
        };
        props.onChange(payload);
    }, [prefix, mainBody])

    return (
        <Space.Compact className={styles.container}>
            {/*<Input style={{}} defaultValue={props.defaultValues.prefix}  />*/}
            <Select
                showSearch
                defaultValue="+372"
                style={{width: '40%'}}
                onChange={(e) => setPrefix(e)}
                options={prefixes}
            />
            <Input style={{width: '80%'}} defaultValue={props.defaultValues.mainBody}
                   onChange={(e) => setMainBody(e.target.value)}/>
        </Space.Compact>
    )

}

export default PhoneNumberInput