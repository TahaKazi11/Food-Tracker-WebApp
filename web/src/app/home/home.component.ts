import { Component, OnInit } from '@angular/core';
import { ApiService } from './../../services/api/api.service';
import { RestaurantList, RestaurantMenu, Restaurant, MenuItem, MenuSection } from 'src/main';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  protected restrauntList: Restaurant[] = [];
  protected restrauntNameList: string[];

  constructor() {
  }

  ngOnInit() {
    this.loadRestaurantsNoLogin();
  }

  private async loadRestaurantsNoLogin() {
    const restraunts = await this.loadRestaurantList();
    // if (!this.restrauntNameList) {
    //   return; // TODO reload button here;
    // }
    restraunts.forEach((restraunt) => {
      ApiService.requestingRestaurant(restraunt)
      .then((data) => {
        this.restrauntList.push(data);
      });
    });
  }

  private loadRestaurantWithAccount(accountId: string) {
    ApiService.requestingRestaurants(accountId)
    .then((data) => {
      if (!data) {
        // TODO display refeshbutton error
      } else {
        this.restrauntList = data;
      }
    });
  }

  private async loadRestaurantList() {
    let restraunts: string[];
    return (await ApiService.requestingRestaurantList()).restaurantlist;
    // .then((data) => {
    //   if (!data) {
    //     // TODO display refeshbutton error
    //   } else {
    //     restraunts = data.restaurantlist;

    //   }
    // }).catch((error) => {
    //     return [];
    // });
    // return restraunts;
  }
}
