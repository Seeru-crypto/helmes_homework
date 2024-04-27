import {DatePicker} from "antd";
import {Dayjs} from "dayjs";
import {useEffect, useState} from "react";

interface IDateSelector {
    initialValue: Dayjs
    onChange: (arg0: Dayjs) => void
}

export function DateSelector(props: IDateSelector) {
    const [dateValue, setDateValue] = useState<Dayjs>(props.initialValue);

    useEffect(() => {
        props.onChange(dateValue)
    }, [dateValue])

    return (
        <DatePicker value={dateValue} onChange={setDateValue} />
    )
}

export default DateSelector