import styles from "./userRegistrationForm.module.scss"
import TextInput from "./TextInput.tsx";
import CheckboxInput from "./CheckboxInput.tsx";
import {useState} from "react";
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
                    <span>Username</span>
                    <TextInput placeholder="username" onChange={setUsername}/>
                    <span>Agree to terms</span>
                    <CheckboxInput label="agree to terms"
                                   checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)}/>
                </div>
                <div className="right">
                    <span>Sectors</span>
                    <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={sectorOptions}/>
                    <CustomButton label="submit" onClick={() => submitForm()}/>
                </div>
            </div>
        </div>
    )
}

export default UserRegistrationForm;