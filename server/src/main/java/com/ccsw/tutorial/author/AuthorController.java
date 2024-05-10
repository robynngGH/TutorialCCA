package com.ccsw.tutorial.author;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDTO;
import com.ccsw.tutorial.author.model.AuthorSearchDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
@Tag(name = "Author", description = "API of Author")
@RequestMapping(value = "/author")
@RestController
@CrossOrigin(origins = "*")
public class AuthorController {

    @Autowired
    AuthorService authorService;
    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginado de {@link Author}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link AuthorDTO}
     */
    @Operation(summary = "Find Page", description = "Method that returns a page of Authors")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<AuthorDTO> findPage(@RequestBody AuthorSearchDTO dto) {

        Page<Author> page = this.authorService.findPage(dto);

        return new PageImpl<>(page.getContent().stream().map(e -> mapper.map(e, AuthorDTO.class)).collect(Collectors.toList()), page.getPageable(), page.getTotalElements());
    }

    /**
     * Recupera un listado de autores {@link Author}
     *
     * @return {@link List} de {@link AuthorDTO}
     */
    @Operation(summary = "Find", description = "Method that return a list of Authors")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<AuthorDTO> findAll() {

        List<Author> authors = this.authorService.findAll();

        return authors.stream().map(e -> mapper.map(e, AuthorDTO.class)).collect(Collectors.toList());
    }

    /**
     * Método para crear o actualizar un {@link Author}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates an Author")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody AuthorDTO dto) {

        this.authorService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link Author}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes an Author")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {
        this.authorService.delete(id);
    }

}
