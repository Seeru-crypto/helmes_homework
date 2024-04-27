import {Cascader} from 'antd';
import styles from "./CascaderInput.module.scss"

interface CascaderProps {
    options: ICascaderOptions[]
    selectedSectorsCallback: (value: string[][]) => void
}

export interface ICascaderOptions {
    value: string | number;
    label: string;
    children?: ICascaderOptions[];
    disableCheckbox?: boolean;
}

const CascaderInput = (props: CascaderProps) => {
    function onChange (value: any ): undefined {
        props.selectedSectorsCallback(value)
    }

    return (
        <div className={styles.container}>
            <Cascader
                className={styles.cascader}
                options={props.options}
                onChange={onChange}
                showSearch={true}
                multiple
                maxTagCount="responsive"
            />
        </div>
    )
}

export default CascaderInput