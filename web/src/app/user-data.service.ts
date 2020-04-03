import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User, MenuItem } from 'src/main';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {
  initData: User;
  private userAccount = new BehaviorSubject(this.initData);
  currentUser = this.userAccount.asObservable();

  items: Array<MenuItem> = [];
  private UserItem = new BehaviorSubject(this.items);
  CurrentUserItem = this.UserItem.asObservable();

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
