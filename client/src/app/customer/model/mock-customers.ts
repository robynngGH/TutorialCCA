//static mock data source

import { Customer } from "./Customer";

//when later adding data via backend transactions, ID will be autoincremented
export const CUSTOMER_DATA: Customer[] = [
    { id: 1, name: 'Customer 1' },
    { id: 2, name: 'Customer 2' },
    { id: 3, name: 'Customer 3' },
    { id: 4, name: 'Customer 4' },
    { id: 5, name: 'Customer 5' }
]   