import { Pageable } from "src/app/core/model/page/Pageable";
import { Author } from "./Author";

/**
 * Completes all the data in the pagination data structure for authors
 */
export class AuthorPage {
    content: Author[];
    pageable: Pageable;
    totalElements: number;
}