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
    this.loadRestraunts();
  }

  private loadRestraunts() {
    let restraunts: string[];
    ApiService.requestingRestrauntList()
    .then((data) => {
      if (!data) {
        // TODO display refeshbutton error
      } else {
        restraunts = data.restaurantlist;
      }
    });
    restraunts.forEach((restraunt) => {
      ApiService.requestingRestraunt(restraunt)
      .then((data) => {
        this.restrauntList.push(data);
      });
    });


  }

}
