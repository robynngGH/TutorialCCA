import { Customer } from "src/app/customer/model/Customer";
import { Game } from "src/app/game/model/Game";

export class Loan {
    id: number;
    game: Game;
    customer: Customer;
    start_date: Date;
    end_date: Date;
}