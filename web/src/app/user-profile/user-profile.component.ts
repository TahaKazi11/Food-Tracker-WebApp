import { Component, OnInit } from '@angular/core';

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
  firstname: string = "Michael";
  lastname: string = "Hammerson";
  email: string = "michael.hammerson@mail.utoronto.ca";
  phonenumber: string = "(647) 888-3802";

  constructor() { }

  ngOnInit() {
  }

  edit() {

  }

}
