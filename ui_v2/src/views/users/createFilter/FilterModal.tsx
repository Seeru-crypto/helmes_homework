import styles from "./FilterModal.module.scss"
import {Modal} from "antd";
import {useEffect, useState} from "react";
import TextInput from "../../../components/TextInput/TextInput.tsx";
import FilterSet from "./FilterSet/FilterSet.tsx";
import Button from "../../../components/button/Button.tsx";

interface IFilterModal {
    isModalOpen: boolean,
    isModalOpenCallback: (arg0: boolean) => void
}

function FilterModal(props: IFilterModal) {
    const [groupName, setGroupName] = useState("")
    const [filters, setFilters] = useState<JSX.Element[]>([])
    const [numberOfFilters, setNumberOfFilters] = useState(1)

    useEffect(() => {
        const items = [];
        for (let i = 0; i < numberOfFilters; i++) {
            items.push(<FilterSet numberOfRows={numberOfFilters} key={i} removeCallback={() => removeElementFromFilterSet(i)}/>);
        }
        setFilters(items)
    }, [numberOfFilters])

    const handleCancel = () => {
        props.isModalOpenCallback(false)
        // setIsModalOpen(false);
    };

    const handleOk = () => {
        props.isModalOpenCallback(false)
        // setIsModalOpen(false);
    };

    function removeElementFromFilterSet(index: number) {
        const items = filters.filter((_, i) => i !== index);
        setFilters(items)
        setNumberOfFilters(numberOfFilters - 1)
    }

    return (
        <Modal width={"80vw"} className={styles.container} title="Filter builder" open={props.isModalOpen} onOk={handleOk}
               onCancel={handleCancel}>
            {/*STEP 0: add filter group name*/}
            <div>
                <p>Filter group name</p>
                <TextInput placeholder={"filter group name"} onChange={setGroupName}/>
            </div>
            <Button onClick={() => setNumberOfFilters(numberOfFilters + 1)}>add filters</Button>
            {filters}
        </Modal>
    )
}

export default FilterModal;