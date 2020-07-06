package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.UserService;
import ru.geekbrains.spring.ishop.utils.filters.UserFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    private final UserService userService;
    private final UserFilter userFilter;

    @Autowired
    public UserController(UserService userService, UserFilter userFilter) {
        this.userService = userService;
        this.userFilter = userFilter;
    }

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/user/all";
    }

    @GetMapping("/all")
    public String usersList(@RequestParam Map<String, String> params,
                                 Model model) {
        //инициируем настройки фильтра
        userFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<User> page = userService.findAll(userFilter, "id");//TODO id -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", userFilter.getFilterDefinition());
        //объект страницы продуктов
        model.addAttribute("page", page);
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("roles", roles);
        //активную страницу
        model.addAttribute("activePage", "Users");
        return "amin/admin/users";
    }

    @GetMapping("/edit/{user_id}/user_id")
    public String editUser(@PathVariable Long user_id, Model model,
                           HttpSession session) {
        User user;
        if(user_id != 0) {
            user = userService.findById(user_id);
        } else {
            user = (User) session.getAttribute("user");
        }
        model.addAttribute("user", user);

        //TODO доделать как ProductController

        return "amin/admin/user-form";
    }

    @GetMapping("/delete/{user_id}/user_id")
    public String deleteUser(@PathVariable Long user_id) {
        User user = userService.findById(user_id);
        userService.delete(user);
        return "redirect:/admin/user/all";
    }

//    @PostMapping("/process/create")
//    public RedirectView processCreateUser(@ModelAttribute @Valid SystemUser systemUser,
//                                BindingResult bindingResult) {
////        if(bindingResult.hasErrors()){
////            //FIXME
////            return "redirect:/admin/product/category/form";
////        }
////        categoryService.save(category);
//        return new RedirectView("/amin/admin/user/all");
//    }

    @PostMapping("/process/edit")
    public RedirectView updateUser(@ModelAttribute @Valid User user,
                            BindingResult bindingResult, HttpSession session) {
        //TODO доделать

        return new RedirectView("/amin/admin/user/all");
    }

}
