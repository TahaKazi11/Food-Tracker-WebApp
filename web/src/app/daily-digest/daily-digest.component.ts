import { Component, OnInit } from '@angular/core';
import { UserDataService } from '../user-data.service';
import { ApiService } from 'src/services/api/api.service';

@Component({
  selector: 'app-daily-digest',
  templateUrl: './daily-digest.component.html',
  styleUrls: ['./daily-digest.component.scss']
})
export class DailyDigestComponent implements OnInit {
  public userId: string;
  public logged_in = false;
  public history:Object[];
  constructor(private user: UserDataService) { }

  ngOnInit() {
    this.userId = this.user.getUserId();
    if (this.userId){
      this.logged_in = true;
      this.loadDailyIntake();
    }
  }
  
  private async loadDailyIntake(){
    ApiService.getDailyIntake(this.userId)
    .then((data)=>{
      if(!data) {
        this.history = {} as Object[];
      } else {
        this.history = Object(data);
      }
    })
  }
}
