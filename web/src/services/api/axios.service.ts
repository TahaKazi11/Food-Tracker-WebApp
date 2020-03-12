import axios, { AxiosResponse, AxiosRequestConfig } from 'axios';

const name = 'AxiosService';

export class AxiosService {

    private static instance: AxiosService;

    private constructor() {}

    public static getInstance(): AxiosService {
        if (!AxiosService.instance) {
            AxiosService.instance = new AxiosService();
        }

        return AxiosService.instance;
    }

    public get(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<any>> {
        return axios.get(url, config);
      }

    public delete(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<any>> {
        return axios.delete(url, config);
    }

    public post(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<any>> {
        return axios.post(url, config);
    }

    public put(url: string, config?: AxiosRequestConfig): Promise<AxiosResponse<any>> {
        return axios.put(url, config);
    }
}
