import { Component, OnInit, Input } from '@angular/core';
import {  Restaurant } from 'src/main';


@Component({
  selector: 'app-restaurant-summary',
  templateUrl: './restaurant-summary.component.html',
  styleUrls: ['./restaurant-summary.component.scss']
})
export class RestaurantSummaryComponent implements OnInit {

  @Input() restaurant: Restaurant;
  constructor() { }

  ngOnInit() {
  }
}
