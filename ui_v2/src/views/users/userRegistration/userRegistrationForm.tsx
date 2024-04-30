import styles from "./userRegistrationForm.module.scss"
import TextInput from "../../../components/TextInput/TextInput.tsx";
import CheckboxInput from "./CheckboxInput/CheckboxInput.tsx";
import {useEffect, useState} from "react";
import CascaderInput from "./CascaderInput/CascaderInput.tsx";
import CustomButton from "../../../components/button/Button.tsx";
import {useAppDispatch, useAppSelector} from "../../../store/store.ts";
import {mapSectorsToIds, mapToOptions} from "../../../util/utils.ts";
import {ISaveUser} from "../../../entities/interfaces/ISaveUser.ts";
import {saveUser, updateUser} from "../../../entities/users.reducer.ts";
import {getSectors} from "../../../entities/sector.reducer.ts";
import PhoneNumberInput, {IPhoneNumberPayload} from "./phoneNumberInput/phoneNumberInput.tsx";
import DateSelector from "../../../components/datePicker/dateSelector.tsx";
import dayjs, {Dayjs} from 'dayjs'

const defaultPhoneNumber: IPhoneNumberPayload = {prefix: "+123", mainBody: "12345678"}

function UserRegistrationForm() {
    const [username, setUsername] = useState('')
    const [selectedSectors, setSelectedSectors] = useState<string[][]>([[]])
    const [agreeToTerms, setAgreeToTerms] = useState<boolean>(false)
    const [phoneNumber, setPhoneNumber] = useState<IPhoneNumberPayload>(defaultPhoneNumber)
    const [email, setEmail] = useState("")
    const [dob, setDob] = useState<Dayjs>(dayjs(new Date()))
    const dispatch = useAppDispatch();
    const sectors = useAppSelector((state) => state.sectors.sectors)
    const userId = useAppSelector((state) => state.user.currentUserID)

    useEffect(() => {
        if (sectors.length === 0) dispatch(getSectors())
    }, [dispatch, sectors])


    useEffect(() => {
        console.log(phoneNumber.prefix, " ", phoneNumber.mainBody)
    }, [phoneNumber])

    function submitForm() {
        const formattedPhoneNumber = `${phoneNumber?.prefix} ${phoneNumber?.mainBody}`
        console.log({formattedPhoneNumber})

        const payload: ISaveUser = {
            name: username,
            agreeToTerms: agreeToTerms,
            sectorIds: mapSectorsToIds(selectedSectors, sectors),
            email,
            phoneNumber: formattedPhoneNumber,
            dob: dob.format()
        }

        userId === "" ? dispatch(saveUser(payload)) : dispatch(updateUser({userId, payload}))
    }

    return (
        <div className={styles.container}>

            <div className={styles.inputContainer}>
                <span>Username</span>
                <TextInput placeholder="username" onChange={setUsername}/>
            </div>

            <div className={styles.inputContainer}>
                <span>Sectors</span>
                <CascaderInput selectedSectorsCallback={(e) => setSelectedSectors(e)} options={mapToOptions(sectors)}/>
            </div>

            <div className={styles.inputContainer}>
                <span>Phone number</span>
                <PhoneNumberInput onChange={(e) => setPhoneNumber(e)} defaultValues={phoneNumber} />
            </div>

            <div className={styles.inputContainer}>
                <span>Email aadress</span>
                <TextInput placeholder="email" onChange={setEmail}/>
            </div>

            <div className={styles.inputContainer}>
                <span>Date of birth</span>
                <DateSelector initialValue={dob} onChange={(e) => setDob(e)} />
            </div>

            <div className={styles.inputContainer}>
                <CheckboxInput label="agree to terms"
                               checkboxState={(newValue: boolean) => setAgreeToTerms(newValue)}/>
            </div>
            <div className={styles.buttonContainer}>
                <CustomButton onClick={() => submitForm()}>Submit</CustomButton>
            </div>
        </div>
    )
}

export default UserRegistrationForm;