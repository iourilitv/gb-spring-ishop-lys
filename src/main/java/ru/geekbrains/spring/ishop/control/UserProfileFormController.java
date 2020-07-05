package ru.geekbrains.spring.ishop.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.UserService;
import ru.geekbrains.spring.ishop.utils.SystemUser;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/profile/form")
public class UserProfileFormController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(UserProfileFormController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showProfileFormPage(HttpSession session, Model theModel) {
        User theUser = (User) session.getAttribute("user");
        theModel.addAttribute("systemUser", new SystemUser(theUser));
        return "amin/profile-form";
    }

    @GetMapping("/change/password/showForm")
    public String showPasswordChangingPage(HttpSession session, Model theModel) {
        User theUser = (User) session.getAttribute("user");
        theModel.addAttribute("systemUser", new SystemUser(theUser));
        return "amin/password-changing-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/change/password/process")
    public String processPasswordChangingForm(
            @Valid @ModelAttribute("systemUser") SystemUser theSystemUser,
            BindingResult theBindingResult, Model theModel) {

        String userName = theSystemUser.getUserName();
        logger.debug("Processing password changing form for: " + userName);
        if (theBindingResult.hasErrors()) {
        return "amin/password-changing-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing == null) {
            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("registrationError", "There is no user with current username!");
            logger.debug("There is no user with current username.");
        return "amin/password-changing-form";
        }
        userService.updatePassword(userName, theSystemUser.getPassword());
        logger.debug("Successfully updated user password: " + userName);
        theModel.addAttribute("confirmationTitle", "Amin | Password Changing Confirmation Page");
        theModel.addAttribute("confirmationMessage", "The password has been changed successfully!");
        theModel.addAttribute("confirmationAHref", "/");
        theModel.addAttribute("confirmationAText", "Go to home page");
        return "amin/confirmation";
    }

}
