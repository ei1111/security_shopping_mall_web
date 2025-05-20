package com.web.board.controller.view;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class BoardViewController {

    @GetMapping("/list")
    public String list() {
        return "board/list";
    }

    @GetMapping("/form")
    public String form(@RequestParam(required = false) Long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "board/form";
    }
}
