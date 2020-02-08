import { ApiError } from './api.error';

export class ApiErrorEmptyContent extends ApiError {
    constructor(public message: string) {
        super(503, message);
    }
}
