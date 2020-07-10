package nowhere.controller;

import nowhere.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("secured")
@PreAuthorize("hasAuthority('isAdmin')")
public class SecuredController {

    @GetMapping
    public String admin(Model model) {
        return "secured";
    }
}
