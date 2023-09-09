import axios from "axios";

const api_basepath = process.env.NEXT_PUBLIC_API_URL;

export async function GetRequest(slug: string) {
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
            return {}
        });
}

export async function PostRequest(slug: string, data: any) {
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
            return {}
        });
}

export async function DeleteRequest(slug: string, name: string) {
    const fullPath = `${api_basepath}/${slug}?name=${name}`
    return await axios({
        method: 'delete',
        url: fullPath,
        responseType: "json",
    })
        .then(function (response) {
            if (response.status === 200) {
                return response.data
            }
        }).catch(function (error) {
            return {}
        });
}
