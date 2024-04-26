import {DatePicker} from "antd";
import {Dayjs} from "dayjs";


export function DateSelector() {
    // const test: Dayjs = new Dayjs().format("2024-04-09")


    function onChange_2 (date: Dayjs, dateString: string | string[]) {
        console.log({dateString});
    }

    return (
        <DatePicker onChange={onChange_2} />
    )

}

export default DateSelector