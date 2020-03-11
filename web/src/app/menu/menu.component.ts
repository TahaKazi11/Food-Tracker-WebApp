import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantMenu, Restaurant, MenuItem } from 'src/main';
import { ApiService } from './../../services/api/api.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  public url: string = '';
  public restaurant_name = '';
  public searchTerm = '';

  public menu: MenuItem[];

  constructor(private router: Router) { }

  ngOnInit() {
    this.url = this.router.url;
    console.log(this.url);
    this.restaurant_name = this.url.split('/')[2]
    console.log(this.restaurant_name);
    this.create_menu();
  }

  public create_menu(){
    this.menu = [];
    for(let i = 0; i<10; i++){
      this.menu.push(this.mockMenuItem());
    }
    console.log("created menu");
  }

  public async onSearchSubmission() {
    this.menu = await ApiService.searchingMenuList(this.restaurant_name, this.searchTerm);
  }

  private mockMenuItem(): MenuItem {
    const menuItem = {
      image: 'https://media.discordapp.net/attachments/666763770327990345/679081001019768849/Final_Logo.png?width=571&height=571',
      calories: 10000,
      name: 'Timbit',
      price: 10,
    } as MenuItem;
    return menuItem;
  }
}
