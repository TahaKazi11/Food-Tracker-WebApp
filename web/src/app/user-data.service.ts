import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from 'src/main';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {
  initData: User;
  private userAccount = new BehaviorSubject(this.initData);
  currentUser = this.userAccount.asObservable();

  constructor() { }

  public changeUserAccount(user: User) {
    this.userAccount.next(user);
  }

  public getUserId() {
    return this.userAccount.getValue()._id;
  }

  public emptyUserAccount() {
    this.userAccount.next(null);
  }
}
