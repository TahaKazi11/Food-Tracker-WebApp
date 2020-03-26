import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/services/api/api.service';
import { Restaurant } from 'src/main';

@Component({
  selector: 'app-building-restaurant',
  templateUrl: './building-restaurant.component.html',
  styleUrls: ['./building-restaurant.component.scss']
})
export class BuildingRestaurantComponent implements OnInit {
  public url: string = '';
  public buildingName = '';
  public restaurants :Restaurant[];
  constructor(private router: Router) { }

  ngOnInit(){
    this.url = this.router.url;
    this.buildingName = decodeURIComponent(this.url.split('/')[2]);
    console.log(this.buildingName);
    this.loadRestaurant();
  }

  private async loadRestaurant(){
    this.restaurants = [];
    this.restaurants = await ApiService.requestingRestaurantByBuilding(this.buildingName);
    console.log(this.restaurants);
  }

}
