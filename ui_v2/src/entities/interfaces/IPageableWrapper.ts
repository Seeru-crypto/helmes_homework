export interface IPageableWrapper<T> {
    totalElements:number
    totalPages: number
    first:	boolean
    last:	boolean
    numberOfElements:	number
    size:	number
    content: T[]
    pageable: IPageable,
    number: number,
    sort: ISort,
    empty: boolean
}

interface ISort {
    empty: boolean,
    sorted: boolean,
    unsorted: boolean
}

interface IPageable {
    pageNumber: number,
    pageSize: number,
    sort: ISort,
    offset: number,
    unpaged: boolean,
    paged: boolean
}