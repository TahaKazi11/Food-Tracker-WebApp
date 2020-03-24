import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantMenu, Restaurant, MenuItem, MenuSection } from 'src/main';
import { ApiService } from './../../services/api/api.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  public url: string = '';
  public restaurantName = '';
  public searchTerm = '';
  public hasData = false;

  public menu: MenuSection[];

  constructor(private router: Router) { }

  ngOnInit() {
    this.url = this.router.url;
    this.restaurantName = decodeURIComponent(this.url.split('/')[2]);
    this.createMenu();
    this.hasData = this.menu.length > 0;
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

}
