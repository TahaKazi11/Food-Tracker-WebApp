import { ApiError } from './api.error';

export class ApiErrorForbidden extends ApiError {
    constructor(code, public message: string) {
        super(code, message);
    }
}