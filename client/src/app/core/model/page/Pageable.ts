import { SortPage } from "./SortPage";

/**
 * Makes up the pageable data in the pagination data structure
 */
export class Pageable {
    pageNumber: number;
    pageSize: number;
    sort: SortPage[];
}