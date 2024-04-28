import styles from "./FilterSet.module.scss"
import {useAppSelector} from "../../../../store/store.ts";
import {useEffect, useState} from "react";
import {AllowedValies, IFilterOptions} from "../../../../entities/interfaces/IFilterOption.ts";
import Selector, {ISelectorOptions} from "../../../../components/selector/selector.tsx";

interface IFilterSet {

}
function FilterSet(props: IFilterSet) {
    const filterOptions = useAppSelector((state) => state.filters.filterOptions);
    const fieldNames = useAppSelector((state) => state.filters.allFieldNames);

    const [formattedFieldNames, setFormattedFieldNames] = useState<ISelectorOptions[]>([])
    const [formattedCriteria, setFormattedCriteria] = useState<ISelectorOptions[]>([])
    const [fieldName, setFieldNAme] = useState("")
    const [allowedValue, setAllowedValue] = useState<AllowedValies | undefined>()
    const [criteria, setCriteria] = useState<string>("")

    useEffect(() => {
        const res: ISelectorOptions[] = getSelectorOptions(fieldNames)
        setFormattedFieldNames(res)
    }, [fieldNames])

    function setField(selectedFieldName: string) {
        setFieldNAme(selectedFieldName)
        setFormattedCriteria([]);
        setCriteria("")
        getCriteria(selectedFieldName)

    }

    function getCriteria(selectedFieldName: string) {
        const selectedFilterOption: IFilterOptions | undefined = filterOptions
            .find(filter => filter.fieldName === selectedFieldName);
        setFormattedCriteria(getSelectorOptions(selectedFilterOption!.criteria))

        setAllowedValue(selectedFilterOption?.allowedValue);
    }

    function getSelectorOptions(arr: string[]): ISelectorOptions[] {
        return arr.map(field => {
            return {
                value: field,
                label: field
            }
        })
    }

    function getValueFiled() {
        switch (allowedValue) {
            case "STRING":
                return (<p>string</p>)
            case "DATE":
                return (<p>DATE</p>)
            case "NUMBER":
                return (<p>NUMBER</p>)
        }

    }

    return (
        <div className={styles.container}>
            <div>
                <p>Select field name</p>
                <Selector onChange={(e) => setField(e)} value={fieldName} options={formattedFieldNames}  />
            </div>

            <div>
                <p>Select criteria</p>
                <Selector isDisabled={fieldName === ""} onChange={setCriteria} value={criteria} options={formattedCriteria}  />
            </div>
            <div>
                {/*TODO: should be either text, date or number field/*/}
                <p>Select Value</p>
                {getValueFiled()}
            </div>

        </div>
    )

}

export default FilterSet