import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'src/main';
import { ApiService } from '../../services/api/api.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  public items: Array<MenuItem> = [];
  public priceTotal = 0;
  private accountId = '5e69a888f1d54f6852d380b0';
  public confMessage = '';
  public success: boolean;
  public showAlert: boolean;
  public exceeded: boolean;

  constructor() { }

  ngOnInit() {
    this.pushItemToCart({ id: '1', Name: 'Cheddar Bacon Uncle Burger Combo', Calories: 350, Price: 5.5, amount: 3 });
    this.pushItemToCart({ id: '2', Name: 'hamburger', Calories: 250, Price: 3.25, amount: 1 });
    this.pushItemToCart({ id: '3', Name: 'Pan-Fried Beef Rice', Calories: 400, Price: 13.99, amount: 2 });
    this.pushItemToCart({ id: '4', Name: 'Secret Chicken with Rice', Calories: 500, Price: 13.88, amount: 3 });
    this.calTotalExpense(this.items);
    this.showAlert = false;
    this.exceeded = false;
  }

  public pushItemToCart(data: MenuItem) {
    this.items.push(data);
  }

  public addFood(itemId: string) {
    const addIndex = this.items.map((item) => item.id).indexOf(itemId);
    this.items[addIndex].amount += 1;
    this.calTotalExpense(this.items);
  }

  public removeFood(itemId: string) {
    const removeIndex = this.items.map((item) => item.id).indexOf(itemId);
    const amountLeft = this.items[removeIndex].amount;

    if (amountLeft > 1) {
      this.items[removeIndex].amount -= 1;
    } else {
      this.items.splice(removeIndex, 1);
    }
    this.calTotalExpense(this.items);
  }

  public calTotalExpense(items: Array<MenuItem>) {
    this.priceTotal = 0;
    items.forEach((item) => {
      this.priceTotal += parseFloat((item.Price * item.amount).toPrecision(3));
    });
  }

  public getTotalExpense() {
    return this.priceTotal;
  }

  public sendExpenseToApi() {
    this.showAlert = true;

    ApiService.deductExpense(this.accountId, this.getTotalExpense().toPrecision(3))
    .then((data) => {
      this.success = true;
      this.confMessage = 'Order confirmed successfully!';

      if(data.exceeded.valueOf()) {
        this.exceeded = true;
      }

    })
    .catch((error) => {
      this.success = false;
      this.confMessage = 'The request did not go through.';
    });
  }


}
