import styles from "./userRegistrationForm.module.scss"
import TextInput from "./TextInput.tsx";
import CheckboxInput from "./CheckboxInput.tsx";
import React, {useState} from "react";
import CascaderInput from "./CascaderInput.tsx";
import {CascaderProps} from "antd";
import CustomButton from "./Button.tsx";

function UserRegistrationForm() {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const [sectorOptions, setSectorOptions] = useState<CascaderProps[]>([])


    function submitForm() {


    }

    return (
        <div className={styles.container}>
            <div className="registration">
                <div className="left">
                    <TextInput placeholder="username" onChange={setUsername}/>
                    <CheckboxInput label="agree to terms"
                                   checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)}/>
                </div>
                <div className="right">
                    <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={sectorOptions}/>
                    <CustomButton label="submit" onClick={() => submit()}/>
                </div>
            </div>
        </div>
    )
}

export default UserRegistrationForm;