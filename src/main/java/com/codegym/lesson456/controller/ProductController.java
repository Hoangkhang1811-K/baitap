package com.codegym.lesson456.controller;
import com.codegym.lesson456.model.*; import com.codegym.lesson456.service.ProductService;
import org.springframework.beans.factory.annotation.Value; import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.nio.file.*; import java.util.Locale; import java.util.UUID;
@Controller @RequestMapping("/products") public class ProductController {
    private final ProductService service; @Value("${upload.directory}") private String uploadDirectory;
    public ProductController(ProductService s){service=s;}
    @GetMapping public String list(Model m){m.addAttribute("products",service.findAll());return "products/list";}
    @PostMapping("/{id}/rate") public String rate(@PathVariable Long id, @RequestParam int rating, RedirectAttributes ra){
        service.rate(id,rating); ra.addFlashAttribute("message","Cảm ơn bạn đã đánh giá ảnh"); return "redirect:/products";
    }
    @GetMapping("/create") public String create(Model m){m.addAttribute("productForm",new ProductForm());return "products/form";}
    @PostMapping("/save") public String save(@ModelAttribute ProductForm form,RedirectAttributes ra) throws Exception {
        Product p=new Product();p.setId(form.getId());p.setName(form.getName());p.setDescription(form.getDescription());
        MultipartFile file=form.getImage();
        if(file!=null&&!file.isEmpty()){
            String original=StringUtils.cleanPath(file.getOriginalFilename()==null?"upload":file.getOriginalFilename());
            String ext=StringUtils.getFilenameExtension(original); String lower=ext==null?"":ext.toLowerCase(Locale.ROOT);
            if(!java.util.Set.of("jpg","jpeg","png","gif","webp").contains(lower)) throw new IllegalArgumentException("Only image files are accepted");
            String stored= UUID.randomUUID()+"."+lower; Path dir=Path.of(uploadDirectory).toAbsolutePath().normalize();
            Files.createDirectories(dir); file.transferTo(dir.resolve(stored)); p.setImage(stored);
        } else if(form.getId()!=null) { service.findAll().stream().filter(x->x.getId().equals(form.getId())).findFirst().ifPresent(x->p.setImage(x.getImage())); }
        service.save(p);ra.addFlashAttribute("message","Product saved");return "redirect:/products";
    }
}
