import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api/api.service';
import { UserDataService } from '../user-data.service';
import { formatDate } from '@angular/common';
import { History } from 'src/main';
import { stringify } from 'querystring';

@Component({
  selector: 'app-daily-digest',
  templateUrl: './daily-digest.component.html',
  styleUrls: ['./daily-digest.component.scss']
})
export class DailyDigestComponent implements OnInit {
  public userId: string;
  public dates: string[];
  public logged_in = true ;
  public now: Date = new Date();
  public historys:History[];
  constructor(private data: UserDataService) { }

  ngOnInit() {
    this.data.currentUser.subscribe(user => this.userId = user._id);
    if (this.userId){
      this.logged_in = true;
    }
    this.dates = [];
    this.historys = [];
    this.get_recent_7_days();
    console.log(this.dates);
    for (var i=0;i<7;i++){
      this.historys.push(this.mockHistory(this.dates[i]));
    }
    console.log(this.historys);
  }
  
  public get_recent_7_days(){
    for (var i=0; i<7;i++){
      var d = new Date();
      d.setDate(d.getDate() - i)
      this.dates.push(formatDate(d,'yyyyMMdd','en-US'));
    }
  }

  public mockHistory(date : string):History{
    const history ={
      cost: 100,
      date: date
    }as History
    return history;
  }
}
