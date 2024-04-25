import {useEffect} from "react";
import {IPage} from "../../entities/interfaces/IPage.ts";
import {getUsers} from "../../entities/users.reducer.ts";
import {useAppDispatch, useAppSelector} from "../../store/store.ts";
import {Pagination, PaginationProps, Table} from "antd";


function Users() {
    const users = useAppSelector((state) => state.user.users);
    const totalUsers = useAppSelector((state) => state.user.totalUsers);
    const dispatch = useAppDispatch();
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
        }
    ];

    useEffect(() => {
        const initialPage: IPage = {
            sort: "createdAt,desc",
            size: 10,
            page: 0
        }
        dispatch(getUsers(initialPage))
    }, [dispatch]);

    const onChange: PaginationProps['onChange'] = (pageNumber, pageSize) => {
        const initialPage: IPage = {
            sort: "createdAt,desc",
            size: pageSize,
            page: pageNumber-1
        }
        dispatch(getUsers(initialPage))
    };

    return (
        <div>
            <h1>Users</h1>
            <Table
                rowKey="id"
                pagination={false}
                dataSource={users}
                columns={columns} />
            <Pagination
                showSizeChanger
                onShowSizeChange={onChange}
                onChange={onChange} defaultCurrent={0} total={totalUsers} />
        </div>
    )
}

export  default Users