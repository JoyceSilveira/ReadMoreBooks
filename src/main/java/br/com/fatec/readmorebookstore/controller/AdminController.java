package br.com.fatec.readmorebookstore.controller;

import org.springframework.stereotype.Controller;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;


@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/menu-admin")
    public String menuAdmin(){ return "menu-admin"; }

}
