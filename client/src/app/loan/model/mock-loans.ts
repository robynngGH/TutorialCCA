import { LoanPage } from "./LoanPage";

export const LOAN_DATA: LoanPage = {
    content: [
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
        },
        { id: 6,
            game: { id: 5, title: 'Game 5', age: 3, category: { id: 3, name: 'Category 3'}, author: { id: 3, name: 'Author 3', nationality: 'IT'}},
            customer: { id: 5, name: 'Customer 5'},
            start_date: new Date('2018-05-13'),
            end_date: new Date('2018-05-23')
        },
        { id: 7,
            game: { id: 6, title: 'Game 6', age: 18, category: { id: 4, name: 'Category 4'}, author: { id: 3, name: 'Author 3', nationality: 'IT'}},
            customer: { id: 4, name: 'Customer 4'},
            start_date: new Date('2017-05-13'),
            end_date: new Date('2017-05-23')
        }
    ],
    pageable: {
        pageSize: 5,
        pageNumber: 0,
        sort: [
            {property: "id", direction: "ASC"}
        ]
    },
    totalElements: 7
}