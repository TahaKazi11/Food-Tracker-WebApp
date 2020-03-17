import { Component, OnInit, Input } from '@angular/core';
import { UserDataService } from 'src/app/user-data.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  follower: number = 0;
  following: number = 0;
  post: number = 0;
  imgpath: string = '';
  _id: string;
  username: string;
  email: string;
  gender: string;
  birth: string;
  phone: string;
  private: string;
  budget: string;

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
  }
}
