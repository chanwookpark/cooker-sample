package chanwook.cooker.sample.commerce;

import chanwook.cooker.Cooker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static chanwook.cooker.sample.commerce.AuthenticationUtil.*;

/**
 * Created by chanwook on 2014. 3. 6..
 */
@Controller
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String viewCart(Cooker c, ModelMap model) {
        if (c.hasCookie(AUTH_TOKEN) && c.hasCookie(ACCESS_TOKEN)) {

            //FIXME 임시로 ID 내린다!
            model.put("_userId", "chanwook");
            model.put("_userName", "박찬욱");

            return "viewCart";
        } else {
            logger.debug("로그인이 필요해 로그인 페이지로 이동합니다!");
            return "redirect:/login?" + RETURN_URL + "=/cart";
        }
    }
}
