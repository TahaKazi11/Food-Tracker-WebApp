import {Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantMenu, Restaurant, MenuItem, MenuSection } from 'src/main';
import { ApiService } from './../../services/api/api.service';
import { UserDataService } from 'src/app/user-data.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  private userId: string = 'NA';
  public url: string = '';
  public restaurantName = '';
  public searchTerm = '';
  public hasData = false;
  public searchTag = '';
  public menu: MenuSection[];
  private food_name : string = '';


  constructor(private router: Router, private data: UserDataService) { }

  ngOnInit() {
    this.url = this.router.url;
    this.restaurantName = decodeURIComponent(this.url.split('/')[2]);
    this.createMenu();
    this.hasData = this.menu.length > 0;
    this.userId = this.data.getUserId();
  }

  public async createMenu() {
    this.menu = [];
    const restaurant = await ApiService.requestingMenuFromRestaurant(this.restaurantName);
    this.menu = restaurant.menu;
}

  public async onSearchSubmission() {
    if(this.searchTerm === '') {
      this.createMenu();
    } else  {
      this.searchByName();
    }

  }

  public async searchByName() {
    let menuItems = []
    for(let i =0; i < this.menu.length; i++) {
      menuItems = []
      for(let x = 0; x < this.menu[i].dishes.length; x++) {
        if(this.menu[i].dishes[x].Name.includes(this.searchTerm)) {
          menuItems.push(this.menu[i].dishes[x]);
        }
      }
      this.menu[i].dishes = menuItems;
    }
  }

  public onAddToCard(name: string, calories: string, price: string) {
    // logic for adding to cart here
    console.log(name, calories, price);
  }

  onTagSearchVege() {
    console.log('Vege');
    this.searchTag = 'Vege';

    this.searchByTag();
  }

  public async searchByTag() {
    let menuItems = []
    for(let i =0; i < this.menu.length; i++) {
      menuItems = []
      for (let x = 0; x < this.menu[i].dishes.length; x++) {
        for (let n = 0; n < this.menu[i].dishes[x].Tag.length; n ++) {
          if (this.menu[i].dishes[x].Tag[n].includes(this.searchTag)) {
            menuItems.push(this.menu[i].dishes[x]);
          }
        }
      }
      this.menu[i].dishes = menuItems;
    }
  }
  onTagSearchHalal() {
    console.log('Halal');
    this.searchTag = 'Halal';
    this.searchByTag();
  }

  onTagSearchMeat() {
    console.log('Meat');
    this.searchTag = 'Meat';
    this.searchByTag();
  }

  onTagSearchDrinks() {
    console.log('Drinks');
    this.searchTag = 'Drinks';
    this.searchByTag();
  }

  onTagSearchResume() {
    this.createMenu();
  }

  async Favourite(item: MenuItem) {
    if (this.userId == 'NA') {
    } else {
      this.food_name = item.Name;
      await ApiService.putLikeFood(this.userId, this.food_name);
    }
  }
}
