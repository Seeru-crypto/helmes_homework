import axios, { AxiosError, AxiosResponse } from 'axios';

const TIMEOUT = 1 * 60 * 1000;
axios.defaults.timeout = TIMEOUT;
axios.defaults.baseURL = "http://localhost:8880/";

interface CustomAxiosResponse extends Omit<AxiosResponse, 'data'> {
  data: string[];
}

interface CustomAxiosError extends Omit<AxiosError, 'response'> {
  response: CustomAxiosResponse;
}

export const setupAxiosInterceptors = async (): Promise<void> => {
  const onResponseSuccess = (response: AxiosResponse): AxiosResponse<any, any> => response;
  const onResponseError = async (err: CustomAxiosError): Promise<CustomAxiosError> => {
    console.log("err: ", err)
    const originalErrorMessages: string[] = err.response?.data !== undefined ? err.response?.data : ['Error has occured'];

    // TODO: Implement a better Axios Error handler, where each error will be thrown independently
    console.log({originalErrorMessages})
    const translatedMessage = originalErrorMessages[0];
    throw new Error(translatedMessage);
  };
  axios.interceptors.response.use(onResponseSuccess, onResponseError);
};
