import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));

export interface RestaurantList {
  'restaurantlist': string[];
}

export interface RestaurantMenu {
  'menu': MenuSection[];
}

export interface MenuSection {
  'tag': string;
  'dishes': MenuItem[];
}

export interface MenuItem {
  'id': string; // uniquely identifies menu item
  'Name': string;
  'Calories': number;
  'Price': number;
  'amount': number; // the amount of the same menu item, default to 1
}

export interface Restaurant {
  'name': string;
  'menu': RestaurantMenu;
  'location': string;
  'hours': string;
  'image': string;
}

export interface User {
  '_id': string;
  'name': string;
  'email': string;
  'gender': string;
  'birth': string;
  'phone': string;
  'private': string;
  'budget': string;
}

export interface Deduction {
  '_id': string;
  'totalExpense': string;
}
