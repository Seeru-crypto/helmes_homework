import styles from "./FilterModal.module.scss"
import {Modal} from "antd";
import {useEffect, useState} from "react";
import {useAppDispatch, useAppSelector} from "../../../store/store.ts";
import TextInput from "../../../components/TextInput/TextInput.tsx";
import FilterSet from "./FilterSet/FilterSet.tsx";

interface IFilterModal {
    isModalOpen: boolean,
    isModalOpenCallback:(arg0: boolean) => void
}

function FilterModal(props: IFilterModal) {
    const filterOptions = useAppSelector((state) => state.filters.filterOptions);
    const dispatch = useAppDispatch();
    const [groupName, setGroupName] = useState("")
    const [filters, setFilters] = useState< JSX.Element[]>([])
    const [numberOfFilters, setNumberOfFilters] = useState(1)

    useEffect(() => {
        console.log({numberOfFilters})

        const items = [];
        for (let i = 0; i < numberOfFilters; i++) {
            items.push(<FilterSet key={i} removeCallback={() => removeElementFromFilterSet(i)}  />);
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

    // const renderFilters = () => {
    //     const items = [];
    //     for (let i = 0; i < numberOfFilters; i++) {
    //         items.push(<FilterSet key={i} removeCallback={() => removeElementFromFilterSet(i)}  />);
    //     }
    //     return items;
    //     setFilters(items)
    // };


    function removeElementFromFilterSet(index: number) {
        const items = filters.filter((_, i) => i !== index);
        setFilters(items)
        setNumberOfFilters(numberOfFilters - 1)
    }

    return (
        <Modal className={styles.container} title="Filter builder" open={props.isModalOpen} onOk={handleOk} onCancel={handleCancel}>
            {/*STEP 0: add filter group name*/}
            <div>
                <p>Filter group name</p>
                <TextInput placeholder={"filter group name"} onChange={setGroupName} />
            </div>

            <button onClick={() => setNumberOfFilters(numberOfFilters + 1)}>add filters</button>

            {/*{renderFilters()}*/}
            {filters}
            {/*STEP 1: Select field*/}
            {/*STEP 2: Select criteria*/}
            {/*STEP 3: enter value*/}
        </Modal>


    )

}

export default FilterModal;