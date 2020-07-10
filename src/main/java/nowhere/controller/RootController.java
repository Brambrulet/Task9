package nowhere.controller;

import javax.servlet.http.HttpServletRequest;
import nowhere.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String index(Model model) {
        return "index";
    }

    @GetMapping("login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }

    @ModelAttribute
    public void commonAttributes(HttpServletRequest request, Model model) {
        model.addAttribute("isAdmin", request.isUserInRole(RoleService.ADMIN_ROLE_NAME));
    }
}
