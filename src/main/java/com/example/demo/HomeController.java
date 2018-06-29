package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    DishRepository dishRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String displayHome(Model model) {
        model.addAttribute("dishes", dishRepository.findAllByOrderByPublicationDateDesc());
        return "index";
    }

    @GetMapping("/newdish")
    public String addDish(Model model) {
        model.addAttribute("dish", new Dish());
        return "newdish";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("dish") Dish dish,
                              BindingResult result,
                              @RequestParam("file")MultipartFile file) {
        if (result.hasErrors()) {
            return "newdish";
        }

        if (file.isEmpty()) {
            /*dish.setPublicationDate(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss")));
            dishRepository.save(dish);*/
            return "redirect:/newdish";
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            dish.setImageCloudinary(uploadResult.get("url").toString());
            dish.setPublicationDate(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss")));
            dishRepository.save(dish);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/newdish";
        }
        return "redirect:/";
    }

    @RequestMapping("/like/{id}")
    public String likeCounter(@PathVariable("id") long id){
        Dish dish =  dishRepository.findById(id).get();
        dish.setLikeCounter(dish.getLikeCounter() + 1);
        dishRepository.save(dish);
        return "redirect:/";
    }

    @RequestMapping("/dislike/{id}")
    public String dislikeCounter(@PathVariable("id") long id){
        Dish dish =  dishRepository.findById(id).get();
        dish.setDislikeCounter(dish.getDislikeCounter() + 1);
        dishRepository.save(dish);
        return "redirect:/";
    }

    @RequestMapping("/update/{id}")
    public String updateDish( @PathVariable("id") long id, Model model){
        model.addAttribute("dish", dishRepository.findById(id).get());
        return "newdish";
    }

    @RequestMapping("/delete/{id}")
    public  String deleteDish(@PathVariable("id") long id){
        dishRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String showSearchResults(HttpServletRequest request, Model model)
    {
        //Get the search string from the result form
        String searchString = request.getParameter("search");
        model.addAttribute("search", searchString);
        model.addAttribute("dishes",
                dishRepository.findAllByNameContainingIgnoreCase(searchString));
        return "index";
    }
}