import {useEffect, useState} from "react";
import {IPage} from "../../entities/interfaces/IPage.ts";
import {getUsers} from "../../entities/users.reducer.ts";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {Pagination, PaginationProps, Table} from "antd";
import FilterModal from "./createFilter/FilterModal.tsx";
import {getFilterOptions} from "../../entities/filters.reducer.ts";

function Users() {
    const users = useAppSelector((state) => state.user.users);
    const totalUsers = useAppSelector((state) => state.user.totalUsers);
    const filterOptions = useAppSelector((state) => state.filters.filterOptions);
    const dispatch = useAppDispatch();
    const [isModalOpen, setIsModalOpen] = useState(false)
    const columns = [
        {
            title: 'user id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'Date of birth',
            dataIndex: 'dob',
            key: 'dob',
            render: (dob: string) => formatDob(dob)
        },
        {
            title: 'Phone number',
            dataIndex: 'phoneNumber',
            key: 'phoneNumber',
        },
        {
            title: 'Sectors',
            dataIndex: 'sectors',
            key: 'sectors',
            render: (sector: string[]) => formatSectors(sector)
        }
    ];

    function formatDob(dob: string) {
        if (!dob) return ""
        return new Date(dob).toLocaleDateString()
    }

    function formatSectors(sectors: string[]) {
        return sectors.map((sector, index) => {
            return <span key={index}>{sector}, </span>
        })
    }

    useEffect(() => {
        const initialPage: IPage = {
            sort: "createdAt,desc",
            size: 10,
            page: 0
        }
        dispatch(getUsers(initialPage))

        if (filterOptions.length === 0) dispatch(getFilterOptions())
    }, [dispatch]);

    const onChange: PaginationProps['onChange'] = (pageNumber, pageSize) => {
        const initialPage: IPage = {
            sort: "createdAt,desc",
            size: pageSize,
            page: pageNumber - 1
        }
        dispatch(getUsers(initialPage))
    };

    return (
        <div>
            <div>
                <h2>Filters</h2><p>
            </p>
                <button>Select existing filter</button>
                <br/>
                <button onClick={() => setIsModalOpen(true)}>
                    Open Modal
                </button>

                <FilterModal isModalOpen={isModalOpen} isModalOpenCallback={(e) => setIsModalOpen(e)}/>

            </div>

            <div>
                <h2>Users</h2>
                <Table
                    rowKey="id"
                    pagination={false}
                    dataSource={users}
                    columns={columns}/>
                <Pagination
                    showSizeChanger
                    onShowSizeChange={onChange}
                    onChange={onChange} defaultCurrent={0} total={totalUsers}/>
            </div>

        </div>
    )
}

export default Users