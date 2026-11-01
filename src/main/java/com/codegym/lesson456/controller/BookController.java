package com.codegym.lesson456.controller;

import com.codegym.lesson456.model.BookLoan;
import com.codegym.lesson456.model.LibraryBook;
import com.codegym.lesson456.service.BookException;
import com.codegym.lesson456.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService service;
    public BookController(BookService service) { this.service = service; }

    @GetMapping public String list(Model model) { model.addAttribute("books", service.findAll()); return "books/list"; }
    @GetMapping("/{id}") public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("book", service.findBook(id)); return "books/detail";
    }
    @PostMapping("/{id}/borrow") public String borrow(@PathVariable Long id, Model model) {
        BookLoan loan = service.borrow(id);
        model.addAttribute("loan", loan);
        return "books/borrowed";
    }
    @GetMapping("/return") public String returnForm() { return "books/return"; }
    @PostMapping("/return") public String giveBack(@RequestParam String code, RedirectAttributes redirect) {
        LibraryBook book = service.giveBack(code);
        redirect.addFlashAttribute("message", "Đã nhận trả sách “" + book.getTitle() + "”.");
        return "redirect:/books";
    }
    @ExceptionHandler(BookException.class)
    public String handleBookError(BookException exception, Model model) {
        model.addAttribute("error", exception.getMessage()); return "books/error";
    }
}
