import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  images: string[];
  email: string = '';

  constructor() {
    this.images = [
      'lily-banse--YHSwy6uqvk-unsplash.jpg',
      'abhishek-sanwa-limbu-LR559Dcst70-unsplash.jpg',
      'eiliv-sonas-aceron-ZuIDLSz3XLg-unsplash.jpg',
      'brooke-lark-M4E7X3z80PQ-unsplash.jpg',
      'brooke-lark-W9OKrxBqiZA-unsplash.jpg',
      'edgar-castrejon-1SPu0KT-Ejg-unsplash.jpg',
      'brenda-godinez-_Zn_7FzoL1w-unsplash.jpg',
      'monika-grabkowska-P1aohbiT-EY-unsplash.jpg',
      'brooke-lark-HlNcigvUi4Q-unsplash.jpg'
    ];
  }

  ngOnInit() {
  }

}
