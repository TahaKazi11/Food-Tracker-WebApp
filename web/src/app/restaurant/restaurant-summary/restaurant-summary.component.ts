import { Component, OnInit, Input } from '@angular/core';
import {  Restaurant } from 'src/main';

@Component({
  selector: 'app-restaurant-summary',
  templateUrl: './restaurant-summary.component.html',
  styleUrls: ['./restaurant-summary.component.scss']
})
export class RestaurantSummaryComponent implements OnInit {

  @Input() restaurant: Restaurant;
  protected src: string;
  constructor() { }

  ngOnInit() {
    this.src = this.getRestrauntImgSrc(this.restaurant.name);
  }

  protected getRestrauntImgSrc(name: string) {
    return `../../assets/img/logos/${name.replace(/[^A-Z0-9]-/ig, '')}.png`;
  }
}
