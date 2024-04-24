import styles from "./userRegistrationForm.module.scss"
import TextInput from "./TextInput/TextInput.tsx";
import CheckboxInput from "./CheckboxInput/CheckboxInput.tsx";
import {useEffect, useState} from "react";
import CascaderInput from "./CascaderInput/CascaderInput.tsx";
import {CascaderProps} from "antd";
import CustomButton from "./Button.tsx";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {getSectors} from "../../entities/sector.reducer.ts";
import {mapToOptions} from "../../util/utils.ts";

function UserRegistrationForm() {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const [sectorOptions, setSectorOptions] = useState<CascaderProps[]>([])
    const dispatch = useAppDispatch();
    const sectors = useAppSelector((state) => state.sectors.sectors)

    useEffect(() => {
        dispatch(getSectors())
    }, [dispatch])

   function submitForm() {
       console.log("submitting user data")
    }

    return (
        <div className={styles.container}>
                <div className="left">
                    <span>Username</span>
                    <TextInput placeholder="username" onChange={setUsername}/>
                    <span>Agree to terms</span>
                    <CheckboxInput label="agree to terms"
                                   checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)}/>
                </div>
                <div className="right">
                    <span>Sectors</span>
                    <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={mapToOptions(sectors)}/>
                    <CustomButton label="submit" onClick={() => submitForm()}/>
                </div>
        </div>
    )
}

export default UserRegistrationForm;