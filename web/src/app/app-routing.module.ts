import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { DailyDigestComponent } from './daily-digest/daily-digest.component';
import { MenuComponent } from './menu/menu.component';
import { RegisterComponent } from './register/register.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { CartComponent } from './cart/cart.component';
import { BuildingComponent } from './building/building.component';
import { BuildingRestaurantComponent } from './building-restaurant/building-restaurant.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'building', component: BuildingComponent },
  { path: 'building/:id', component: BuildingRestaurantComponent },
  { path: 'login', component: LoginComponent },
  { path: 'DailyDigest', component: DailyDigestComponent },
  { path: 'Menu/:id', component: MenuComponent },
  { path: 'Register', component: RegisterComponent },
  { path: 'UserProfile', component: UserProfileComponent },
  { path: 'Cart', component: CartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
