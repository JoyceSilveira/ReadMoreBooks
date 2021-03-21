package br.com.fatec.readmorebookstore.controller;

import br.com.fatec.readmorebookstore.TodoService;
import br.com.fatec.readmorebookstore.dominio.Todo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integration-test")
public class IntegrationTestRestController {
    private final TodoService service;

    public IntegrationTestRestController(TodoService service) {
        this.service = service;
    }

    @PostMapping("/clear-all-todos")
    public void clearAllTodos() {
        service.deleteAll();
    }

    @PostMapping("/prepare-todo-list-items")
    public void prepareTodoListItems() {
        service.addTodoItem(new Todo("Add Cypress tests"));
        service.addTodoItem(new Todo("Write blog post"));
    }
}
