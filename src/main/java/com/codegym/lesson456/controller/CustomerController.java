package com.codegym.lesson456.controller;
import com.codegym.lesson456.model.Customer; import com.codegym.lesson456.service.CustomerService;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller @RequestMapping("/customers") public class CustomerController {
    private final CustomerService service; public CustomerController(CustomerService s){service=s;}
    @GetMapping public String list(Model model){model.addAttribute("customers",service.findAll());return "customers/list";}
    @GetMapping("/create") public String create(Model model){model.addAttribute("customer",new Customer());return "customers/form";}
    @GetMapping("/{id}/edit") public String edit(@PathVariable long id,Model model){Customer c=service.findById(id);if(c==null)return "redirect:/customers";model.addAttribute("customer",c);return "customers/form";}
    @PostMapping("/save") public String save(@ModelAttribute Customer customer,RedirectAttributes ra){service.save(customer);ra.addFlashAttribute("message","Customer saved");return "redirect:/customers";}
    @PostMapping("/{id}/delete") public String delete(@PathVariable long id,RedirectAttributes ra){service.delete(id);ra.addFlashAttribute("message","Customer deleted");return "redirect:/customers";}
}
