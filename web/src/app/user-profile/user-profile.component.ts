import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { UserDataService } from 'src/app/user-data.service';
import { ApiService } from '../../services/api/api.service';
import Axios from 'axios';
import { AxiosService } from 'src/services/api/axios.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  follower: number = 0;
  following: number = 0;
  post: number = 0;
  _id: string;
  username: string;
  email: string;
  gender: string;
  birth: string;
  phone: string;
  private: string;
  budget: string;
  newBudget: string;
  editActive: boolean;
  showAlert: boolean;

  constructor(private data: UserDataService) { }

  ngOnInit() {
    this.data.currentUser.subscribe((user) => {
      this._id = user._id;
      this.username = user.name;
      this.email = user.email;
      this.gender = user.gender;
      this.birth = user.birth;
      this.phone = user.phone;
      this.private = user.private;
      this.budget = user.budget;
    });

    this.editActive = false;
    this.showAlert = false;
    this.newBudget = '';
  }

  public enableEdit() {
    if (this._id == null) {
      this.showAlert = true;
    } else {
      this.editActive = true;
    }
  }


  public validateBudget() {
    const budgetRe = /^[0-9]+$/;

    if (this.newBudget.match(budgetRe)) {
      return true;
    }

    return false;
  }

  public editBudget() {
    ApiService.editBudget(this._id, this.newBudget)
    .then((data) => {
    });

    ApiService.getProfile(this._id)
    .then((user) => {
      this.data.changeUserAccount(user);
    });

    this.ngOnInit();
  }
}
