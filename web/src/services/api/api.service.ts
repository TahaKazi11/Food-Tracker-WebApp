import { AxiosService } from './axios.service';
import { AxiosResponse} from 'axios';
import { ApiErrorBadRequest } from './errors/api.error.bad.request.error';
import { ApiErrorForbidden } from './errors/api.error.forbidden';
import { ApiErrorTechnical } from './errors/api.error.technical';
import { ApiErrorEmptyContent } from './errors/api.error.empty.content';
import { ApiErrorEmptyResponse } from './errors/api.error.empty.response';
import { ApiError } from './errors/api.error';

const apiBase = () => {
    return 'http://frontend.northbuilding.ca:27580/v2';
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
      ) => `${ apiBase() }/menu/by-restaurants/${ restaurantId }`
    },
};

export class ApiService {

    private static axiosService            = AxiosService.getInstance();


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

