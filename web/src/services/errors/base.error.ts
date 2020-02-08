export class BaseError extends Error {
    public displayMessage;
    public level;
    public timestamp;

    public className;

    constructor(public message: string, {
      displayMessage = message,
      timestamp = new Date().toISOString()
    } = {}) {
      super(message);
      this.className = this.constructor.name;

      this.displayMessage = displayMessage;
      this.timestamp      = timestamp;
    }

    public wrap(error: Error) {
      this.message += '\nWrapped error message:\n' + error.message;
      this.stack = error.stack;
      return this;
    }
}
