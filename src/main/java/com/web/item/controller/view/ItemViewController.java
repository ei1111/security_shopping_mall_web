package com.web.item.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemViewController {
    @GetMapping("/createItemForm")
    public String saveForm() {
        return "item/createItemForm";
    }

    @GetMapping("/register")
    public String itemRegisterForm() {
        return "item/itemRegisterForm";
    }

    @GetMapping("/{itemId}/edit")
    public String updateForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "item/updateItemForm";
    }

    @GetMapping("/itemList")
    public String itemListForm() {
        return "item/itemListForm";
    }

    @GetMapping("{itemId}/detail")
    public String itemDetailForm(@PathVariable Long itemId, Model model) {
        model.addAttribute("itemId", itemId);
        return "item/itemDetailForm";
    }
}
