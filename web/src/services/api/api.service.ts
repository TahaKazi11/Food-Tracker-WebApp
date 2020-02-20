import { AxiosService } from './axios.service';
import { AxiosResponse} from 'axios';
import { ApiErrorBadRequest } from './errors/api.error.bad.request.error';
import { ApiErrorForbidden } from './errors/api.error.forbidden';
import { ApiErrorTechnical } from './errors/api.error.technical';
import { ApiErrorEmptyContent } from './errors/api.error.empty.content';
import { ApiErrorEmptyResponse } from './errors/api.error.empty.response';
import { ApiError } from './errors/api.error';
import { Injectable } from '@angular/core';
import { RestaurantList, RestaurantMenu, Restaurant } from 'src/main';


const apiBase = () => {
    return '52.186.68.121:27580/v1'; // IP for server
};

const urls = {
    user : {
      byAccount   : (userAccountId: string,
      ) => `${ apiBase() }/user/by-account-id/${ userAccountId }`,
      settings: (userAccountId: string,
      ) => `${ apiBase() }/user/by-account-id/${ userAccountId }`
    },
    restaurants   : {
      menu: (restaurantId: string,
      ) => `${ apiBase() }/menu/by-restaurants/${ restaurantId }`,
      list: (
        ) => `${ apiBase() }/restaurants`,
      full: (restaurantId: string
        ) => `${ apiBase() }/full-info/by-restaurant-id/${ restaurantId }`,
      byAccount: (userAccountId: string
        ) => `${ apiBase() }/restaurants/by-account-id/${ userAccountId }`,
    },


};

export class ApiService {

    private static axiosService            = AxiosService.getInstance();

    public static requestingRestraunts(userAccountId: string): Promise<Restaurant[]> {
      return this.requestingFromApiWithRetries<Restaurant[]>(urls.restaurants.byAccount(userAccountId), 1); // TODO add try catch for handling a not found restraunt not found
    }

    public static requestingRestraunt(restrauntId: string): Promise<Restaurant> {
      return this.requestingFromApiWithRetries<Restaurant>(urls.restaurants.menu(restrauntId), 1); // TODO add try catch for handling a not found restraunt not found
    }

    public static requestingMenuFromRestraunt(restrauntId: string): Promise<RestaurantMenu> {
      return this.requestingFromApiWithRetries<RestaurantMenu>(urls.restaurants.menu(restrauntId), 1); // TODO add try catch for handlign menu not found
    }

    public static requestingRestrauntList(): Promise<RestaurantList> {
      return this.requestingFromApiWithRetries<RestaurantList>(urls.restaurants.list(), 2); // TODO add try catch for bad gateway
    }

    private static async requestingFromApiWithRetries<T>(path: string, maxRetries = 0, numRetry = 0): Promise<T> {
      try {
        return await ApiService.requestingFromAPI<T>(path);
      } catch (e) {
        const statusCode  = e.response ?  e.response.status :  e.code;
        const isRetryable = statusCode !== 400 && statusCode !== 403 && statusCode !== 204;
        if (isRetryable && numRetry < maxRetries) {
          const delayBase = (statusCode === 429 ? 5000 : 1000);
          const delayMs   = delayBase + numRetry * delayBase;
          await new Promise((resolve) => setTimeout(resolve, delayMs));
          return ApiService.requestingFromApiWithRetries<T>(path, maxRetries, numRetry + 1);
        }
        throw e;
      }
    }


    /**
     * General function used to request from the API.
     * @param path full path for request.
     */

    private static async requestingFromAPI<T>(path: string): Promise<T> {
        let response: AxiosResponse<any>;
        let responseData: T;
        let header: T;

        try {
            response = await this.axiosService.get(path);
          } catch (e) {
            response = e.response;
            header = response.headers;

            switch (response.status) {
              case 400:
                throw new ApiErrorBadRequest(response.status, 'Bad Request');
              case 403:
                throw new ApiErrorForbidden(response.status, `Forbidden request`); // used primarly if you have a request for data that isnt your own.
              case 500:
                throw new ApiErrorTechnical(response.status, header['api-errorMessage']);
              case 502:
                  throw new ApiErrorTechnical(response.status, 'Bad Gateway'); // TODO: If its bad gateway we may want to auto retry in future
              case 504:
                  throw new ApiErrorTechnical(response.status, 'Gateway Timeout'); // TODO: If its a gateway timeout we may want to wait and automatically retry
            }
        }

        responseData = response.data;
        header       = response.headers;

        switch (response.status) {
          case 200:
            const hasContent = responseData && (Array.isArray(responseData) || Object.keys(responseData).length > 0);
            if (!hasContent) {
              throw new ApiErrorEmptyContent('No response data available.');
            } else {
              return responseData;
            }
          case 204:
            throw new ApiErrorEmptyResponse(response.statusText);
          default:
            throw new ApiError(response.status, header['api-errorMessage']);
        }

    }

}

