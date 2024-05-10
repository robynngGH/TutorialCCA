import { Loan } from "./Loan";

export const LOAN_DATA_LIST: Loan[] = [
    { id: 1,
        game: { id: 1, title: 'Game 1', age: 10, category: { id: 1, name: 'Category 1'}, author: { id: 1, name: 'Author 1', nationality: 'BR'}},
        customer: { id: 1, name: 'Customer 1'},
        start_date: new Date('2019-05-13'),
        end_date: new Date('2019-05-23')
    },
    { id: 2,
        game: { id: 2, title: 'Game 2', age: 8, category: { id: 1, name: 'Category 1'}, author: { id: 2, name: 'Author 2', nationality: 'SP'}},
        customer: { id: 2, name: 'Customer 2'},
        start_date: new Date('2020-05-13'), 
        end_date: new Date('2020-05-23')
    },
    { id: 3,
        game: { id: 3, title: 'Game 3', age: 12, category: { id: 2, name: 'Category 2'}, author: { id: 1, name: 'Author 1', nationality: 'BR'}},
        customer: { id: 1, name: 'Customer 1'},
        start_date: new Date('2021-05-13'),
        end_date: new Date('2021-05-23')
    },
    { id: 4,
        game: { id: 4, title: 'Game 4', age: 7, category: { id: 2, name: 'Category 2'}, author: { id: 2, name: 'Author 2', nationality: 'SP'}},
        customer: { id: 3, name: 'Customer 3'},
        start_date: new Date('2022-05-13'),
        end_date: new Date('2022-05-23')
    },
    { id: 5,
        game: { id: 4, title: 'Game 4', age: 7, category: { id: 2, name: 'Category 2'}, author: { id: 2, name: 'Author 2', nationality: 'SP'}},
        customer: { id: 4, name: 'Customer 4'},
        start_date: new Date('2023-05-13'),
        end_date: new Date('2023-05-23')
    }
]