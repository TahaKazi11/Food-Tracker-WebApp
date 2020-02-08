import { ApiError } from './api.error';

export class ApiErrorBadRequest extends ApiError {
    constructor(code, public message: string) {
        super(code, message);
    }
}
