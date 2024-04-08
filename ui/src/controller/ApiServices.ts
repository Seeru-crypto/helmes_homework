import axios from "axios";
import {MessageInstance} from "antd/es/message/interface";

const api_basepath = process.env.NEXT_PUBLIC_API_URL;

export type RequestType = 'inner' | 'outer';

export async function GetRequest(slug: string, type: RequestType, messageApi?: MessageInstance) {

    const fullPath = type === 'outer' ? `${api_basepath}/${slug}` : `${process.env.NEXT_PUBLIC_INNER_API_URL}/${slug}`;
    return await axios({
        method: 'get',
        url: fullPath,
        params: {
            sort: 'id',
            page: 0,
            size: 10
        },
        responseType: "json",
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.data
            }
        }).catch(function (error) {
            if (messageApi) messageApi.error(error.message);
            return {}
        });
}

export async function PostRequest(slug: string, data: any, messageApi: MessageInstance, successMessage: string) {
    const fullUrl = `${api_basepath}/${slug}`;

    return await axios({
        method: 'post',
        url: fullUrl,
        responseType: "json",
        data: data
    })
        .then(function (response) {
            if (response.status === 201) {
                messageApi.success(successMessage);
                return response.data
            }
        }).catch(function (error) {
            messageApi.error(error.message);
            return {}
        });
}

export async function PutRequest(slug: string, data: any, messageApi: MessageInstance, successMessage: string) {
    const fullUrl = `${api_basepath}/${slug}`;

    return await axios({
        method: 'put',
        url: fullUrl,
        responseType: "json",
        data: data
    })
        .then(function (response) {
            if (response.status === 200) {
                messageApi.success(successMessage);
                return response.data
            }
        }).catch(function (error) {
            messageApi.error(error.message);
            return {}
        });
}