package com.fmi.findmeabuddy.controller;
import com.fmi.findmeabuddy.Service.UserService;
import com.fmi.findmeabuddy.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(){
        return "login";
    }



    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String verifyLogin (@RequestParam String userEmail,
                               @RequestParam String password,
                               HttpSession session, Model model){

        Account user = userService.loginUser(accountId, password);
        if (user == null ){
            model.addAttribute("loginError", "Your credentials are not correct. Please try again");
            return "login";
        }
        session.setAttribute("loggedInUser", user);
        return "redirect:/";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.removeAttribute("loggedInUser");
        return "login";
    }
}
