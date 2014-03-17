package chanwook.cooker.sample.commerce;

import chanwook.cooker.Cooker;
import chanwook.cooker.sample.auth.AuthenticationUtil;
import chanwook.cooker.sample.auth.SessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;

import static chanwook.cooker.sample.auth.AuthenticationUtil.*;

/**
 * Created by chanwook on 2014. 3. 6..
 */
@Controller
public class CartController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private SessionStore sessionStore = AuthenticationUtil.getSessionStore();

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String viewCart(Cooker c, ModelMap model) {
        if (c.hasCookie(AUTH_TOKEN) && c.hasCookie(ACCESS_TOKEN)) {

            if (!checkValidRequest(c)) {
                logger.warn("유효하지 않은 ACCESS TOKEN 값 입니다! 다시 로그인해주세요!");
                return "redirect:/login?" + RETURN_URL + "=/cart";
            }

            //새로운 페이지에 접근했으니 token 재발행
            createAccessToken(c);

            model.put("_userId", sessionStore.getSessionAttribute(c.getCookieValue(AUTH_TOKEN), "_userId"));
            model.put("_userName", "박찬욱");

            return "viewCart";
        } else {
            logger.debug("로그인이 필요해 로그인 페이지로 이동합니다!");
            return "redirect:/login?" + RETURN_URL + "=/cart";
        }
    }

    private boolean checkValidRequest(Cooker cooker) {
        Cookie auth = cooker.getCookie(AUTH_TOKEN);
        Cookie access = cooker.getCookie(ACCESS_TOKEN);
        boolean isValid = isValidAccessToken(auth.getValue(), access.getValue());
        return isValid;
    }
}
