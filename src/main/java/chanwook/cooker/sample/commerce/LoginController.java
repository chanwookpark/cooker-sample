package chanwook.cooker.sample.commerce;

import chanwook.cooker.Cooker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

import static chanwook.cooker.sample.commerce.AuthenticationUtil.*;

/**
 * Created by chanwook on 2014. 3. 6..
 */
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Cooker c, HttpServletRequest request, ModelMap model) {
        // 이미 로그인 되어 있는 사용인지 체크
        // TODO ㅇㅜㅅㅓㄴ ㄱㅏㅂㅅㅇㅣ ㅇㅣㅆㄴㅡㄴㅈㅣㅁㅏㄴ ㅊㅔㅋㅡ
        String referer = request.getHeader("referer");
        if (c.hasCookie(AUTH_TOKEN) && c.hasCookie(ACCESS_TOKEN)) {
            // 이미 로그인이 되어 있으면 온 화면으로 다시 돌려보내기!
            logger.info("이미 로그인이 되어 있어 요청온 페이지로 다시 보냄[referer:" + referer + "]");
            return "redirect:" + referer;
        }

        String returnUrl = "";
        if (request.getParameter(RETURN_URL) != null) {
            returnUrl = request.getParameter(RETURN_URL);
        } else {
            returnUrl = referer;
        }
        logger.debug("returnUrl 지정: " + returnUrl);
        model.put(RETURN_URL, returnUrl);

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authencation(HttpServletRequest req, Cooker c) {
        //returnUrl이 없으면 인증 로직을 수행하지 않음!
        String returnUrl = req.getParameter(RETURN_URL);
        if (!StringUtils.hasText(returnUrl)) {
            throw new RuntimeException("ReturnUrl 파라미터가 지정되지 않았습니다!");
        }

        String id = req.getParameter("id");
        String password = req.getParameter("password");

        //TODO 인증 로직 추가~

        // Auth Cooking
        String authToken = AuthenticationUtil.createAuthToken(req);
        String accessToken = AuthenticationUtil.createAccessToken(req);

        c.cooking(AUTH_TOKEN, authToken).sha256(id);
        c.cooking(ACCESS_TOKEN, accessToken).encoding();

        logger.debug("로그인을 성공해 해당 페이지로 이동합니다(to: " + returnUrl + ")");

        return "redirect:" + returnUrl;
    }
}
