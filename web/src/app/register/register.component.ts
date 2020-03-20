import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api/api.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username = '';
  email = '';
  phone = '';
  gender = 'male';
  birth = '';
  password = '';
  confPassword = '';
  success: boolean;
  showAlert: boolean;
  confMessage: string;

  constructor() { }

  ngOnInit() {
    this.showAlert = false;
  }

  public validateUsername() {
    const usernameRe = /^[a-zA-Z0-9]+$/;

    if (this.username.match(usernameRe)) { return true; }

    return false;
  }

  public validateEmail() {
    const emailRe = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if (this.email.match(emailRe)) { return true; }

    return false;
  }

  public validatePhone() {
    const phoneRe = /^\d{10}$/;

    if (this.phone.match(phoneRe)) { return true; }

    return false;
  }

  public validateBirth() {
    const birthRe = /^\d{4}-\d{2}-\d{2}$/;

    if (this.birth.match(birthRe)) { return true; }

    return false;
  }

  public validatePassword() {
    const passwordRe = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;

    if (this.password.match(passwordRe)) {
      return true;
    }

    return false;
  }

  public duplicateConfirmation() {
    if (this.password === this.confPassword) { return true; }

    return false;
  }

  public existsEmptyFields() {
    if (this.username === '' || this.email === '' || this.phone === ''
        || this.birth === '' || this.password === '' || this.confPassword === '') {
      return true;
    }

    return false;
  }

  public validateFields() {
    const userIsValid = this.validateUsername();
    const emailIsValid = this.validateEmail();
    const phoneisValid = this.validatePhone();
    const birthIsValid = this.validateBirth();
    const passwordIsValid = this.validatePassword();
    const passConf = this.duplicateConfirmation();

    if (userIsValid && emailIsValid && phoneisValid
      && birthIsValid && passwordIsValid && passConf) {
      return true;
    }

    return false;
  }

  public registerUser() {
    ApiService.registerUser(this.username, this.email, this.phone, this.gender, this.birth, this.password)
    .then((data) => {
      this.showAlert = true;
      this.success = true;
      this.confMessage = 'You have successfully registered! You can now login.';
    })
    .catch((error) => {
      this.showAlert = true;
      this.success = false;
      this.confMessage = 'Your request didn\'t go through.';
    });
  }

}
