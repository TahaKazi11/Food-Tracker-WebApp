import { Component, OnInit } from '@angular/core';
import { ApiService } from './../../services/api/api.service';
import { RestaurantList, RestaurantMenu, Restaurant, MenuItem } from 'src/main';

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
    //this.fillRestaurantList(); // TODO: Remove this function when we have data from the backend 
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

  // ** MOCKS BELOW DELETE WHEN BACKEND IS READY */
  private fillRestaurantList() {
    this.restrauntList = [];
    for(let i = 0; i < 10; i++) {
      this.restrauntList.push(this.mockRestraunt());
    }
  }

  public mockRestraunt(): Restaurant {
    const temp = {
      hours: '5-5',
      location: 'IB 220',
      image: 'https://media.discordapp.net/attachments/666763770327990345/679081001019768849/Final_Logo.png?width=571&height=571',
      menu: this.mockMenu(),
      name: 'Mock Restaurant'
    } as Restaurant;
    return temp;
  }

  private mockMenu(): RestaurantMenu {
    const menu = {
       menu: [this.mockMenuItem()]
    } as RestaurantMenu;
    return menu;
  }

  private mockMenuItem(): MenuItem {
    const menuItem = {
      calories: 10000,
      name: 'Timbit',
      price: 10,
    } as MenuItem;
    return menuItem;
  }


}
