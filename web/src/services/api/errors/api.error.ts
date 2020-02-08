import { BaseError } from './../../errors/base.error';

export class ApiError extends BaseError {
    constructor(public code: number, public message: string) {
      super(message);
    }
  }

export interface ApiErrorContent {
    error: {
      code: number
      message: string
    };
}

