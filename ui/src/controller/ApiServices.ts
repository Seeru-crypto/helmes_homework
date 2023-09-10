import axios from "axios";
import {MessageInstance} from "antd/es/message/interface";

const api_basepath = process.env.NEXT_PUBLIC_API_URL;

export async function GetRequest(slug: string, messageApi?: MessageInstance  ) {
    const fullPath = `${api_basepath}/${slug}`
    return await axios({
        method: 'get',
        url: fullPath,
        responseType: "json",
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.data
            }
        }).catch(function (error) {
            if (messageApi) messageApi.error(error.message);
            // Filter incomming message to a user friendly format
            return {}
        });
}

export async function PostRequest(slug: string, data: any, messageApi: MessageInstance  ) {
    const fullUrl = `${api_basepath}/${slug}`;

    return await axios({
        method: 'post',
        url: fullUrl,
        responseType: "json",
        data: data
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.data
            }
        }).catch(function (error) {
            // Filter incomming message to a user friendly format
            messageApi.error(error.message);
            return {}
        });
}