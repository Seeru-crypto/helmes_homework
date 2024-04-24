import styles from "./userRegistrationForm.module.scss"
import TextInput from "./TextInput/TextInput.tsx";
import CheckboxInput from "./CheckboxInput/CheckboxInput.tsx";
import {useEffect, useState} from "react";
import CascaderInput from "./CascaderInput/CascaderInput.tsx";
import CustomButton from "./Button.tsx";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {mapSectorsToIds, mapToOptions} from "../../util/utils.ts";
import {ISaveUser} from "../../entities/interfaces/ISaveUser.ts";
import {saveUser} from "../../entities/users.reducer.ts";
import {getSectors} from "../../entities/sector.reducer.ts";

function UserRegistrationForm() {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const dispatch = useAppDispatch();
    const sectors = useAppSelector((state) => state.sectors.sectors)

    useEffect(() => {
        dispatch(getSectors())
    }, [dispatch])

   function submitForm() {
       const payload: ISaveUser = {
           name: username,
           agreeToTerms: agreeToTerms,
           sectorIds: mapSectorsToIds(selectedSectors, sectors)
       }
       dispatch(saveUser(payload))
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