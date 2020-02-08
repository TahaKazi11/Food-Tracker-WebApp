import { ApiError } from './api.error';

export class ApiErrorTechnical extends ApiError {
    constructor(code, public message: string) {
        super(code, message);
    }
}
