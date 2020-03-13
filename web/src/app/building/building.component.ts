import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-building',
  templateUrl: './building.component.html',
  styleUrls: ['./building.component.scss']
})
export class BuildingComponent implements OnInit {
  protected buildingList : String[] = ["Davis","IB","Deerfield","CCT","Kaneff","Maanjiwe Nendamowinan"]
  constructor() { }

  ngOnInit(): void {
  }
  
}
