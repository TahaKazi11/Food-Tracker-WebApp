import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api/api.service';
import { UserDataService } from 'src/app/user-data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  images: string[];
  email = '';
  password = '';
  confMessage = '';
  success: boolean;
  showAlert: boolean;

  constructor(private user: UserDataService) {
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
    this.showAlert = false;
  }

  ngOnInit() {
  }

  public validateEmail() {
    const emailRe = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (this.email.match(emailRe)) {
      return true;
    }

    return false;
  }

  public validatePassword() {
    const passwordRe = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

    if (this.password.match(passwordRe)) {
      return true;
    }

    return false;
  }

  public authenticate() {
    ApiService.authenticateLogin(this.email, this.password)
    .then((data) => {
      this.user.changeUserAccount(data);
      this.success = true;
      this.confMessage = 'Login successful!';
    })
    .catch((error) => {
      this.success = false;
      this.confMessage = 'Either user does not exist or email and password don\'t match.';
    });

    this.showAlert = true;
  }
}
