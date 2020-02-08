import { ApiError } from './api.error';

export class ApiErrorEmptyResponse extends ApiError {
    constructor(public message: string) {
        super(204, message);
    }
}
