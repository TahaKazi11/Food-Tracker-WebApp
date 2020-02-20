import { Component, OnInit } from '@angular/core';
import { ApiService } from './../../services/api/api.service';
import { RestaurantList, RestaurantMenu, Restaurant } from 'src/main';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  protected restrauntList: Restaurant[];

  constructor() {
  }

  ngOnInit() {
    this.loadRestrauntsNoLogin();
  }

  private loadRestrauntsNoLogin() {
    const restraunts = this.loadRestaurantList();
    restraunts.forEach((restraunt) => {
      ApiService.requestingRestraunt(restraunt)
      .then((data) => {
        this.restrauntList.push(data);
      });
    });
  }

  private loadRestrauntsWithAccount(accountId: string) {
    ApiService.requestingRestraunts(accountId)
    .then((data) => {
      if (!data) {
        // TODO display refeshbutton error
      } else {
        this.restrauntList = data;
      }
    });
  }

  private loadRestaurantList(): string[] {
    let restraunts: string[];
    ApiService.requestingRestrauntList()
    .then((data) => {
      if (!data) {
        // TODO display refeshbutton error
      } else {
        restraunts = data.restaurantlist;
      }
    }).catch((error) => {
        return [];
    });
    return restraunts;
  }

}
