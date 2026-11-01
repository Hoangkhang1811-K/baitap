package com.codegym.lesson456.controller;
import com.codegym.lesson456.model.Blog; import com.codegym.lesson456.service.BlogService;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/blogs") public class BlogController {
    private final BlogService service; public BlogController(BlogService s){service=s;}
    @GetMapping public String list(Model model){model.addAttribute("blogs",service.findAll());return "blogs/list";}
    @GetMapping("/create") public String create(Model model){model.addAttribute("blog",new Blog());return "blogs/form";}
    @GetMapping("/{id}") public String detail(@PathVariable long id,Model model){Blog b=service.findById(id);if(b==null)return "redirect:/blogs";model.addAttribute("blog",b);return "blogs/detail";}
    @GetMapping("/{id}/edit") public String edit(@PathVariable long id,Model model){Blog b=service.findById(id);if(b==null)return "redirect:/blogs";model.addAttribute("blog",b);return "blogs/form";}
    @PostMapping("/save") public String save(@ModelAttribute Blog blog,RedirectAttributes ra){service.save(blog);ra.addFlashAttribute("message","Blog saved");return "redirect:/blogs";}
    @PostMapping("/{id}/delete") public String delete(@PathVariable long id,RedirectAttributes ra){service.delete(id);ra.addFlashAttribute("message","Blog deleted");return "redirect:/blogs";}
}
