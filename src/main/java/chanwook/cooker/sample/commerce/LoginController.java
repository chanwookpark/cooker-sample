package chanwook.cooker.sample.commerce;

import chanwook.cooker.Cooker;
import chanwook.cooker.CookiePrototype;
import chanwook.cooker.sample.auth.AuthenticationUtil;
import chanwook.cooker.sample.auth.SessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static chanwook.cooker.sample.auth.AuthenticationUtil.*;

/**
 * Created by chanwook on 2014. 3. 6..
 */
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private SessionStore sessionStore = AuthenticationUtil.getSessionStore();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Cooker c, HttpServletRequest request, ModelMap model) {
        // 이미 로그인 되어 있는 사용인지 체크
        // TODO ㅇㅜㅅㅓㄴ ㄱㅏㅂㅅㅇㅣ ㅇㅣㅆㄴㅡㄴㅈㅣㅁㅏㄴ ㅊㅔㅋㅡ
        String returnUrl = "";
        String referer = request.getHeader("referer");
        if (request.getParameter(RETURN_URL) != null) {
            returnUrl = request.getParameter(RETURN_URL);
        } else {
            returnUrl = referer;
        }
        logger.debug("returnUrl 지정: " + returnUrl);

        if (c.hasCookie(AUTH_TOKEN) && c.hasCookie(ACCESS_TOKEN)) {
            // 이미 로그인이 되어 있으면 온 화면으로 다시 돌려보내기!
            logger.info("이미 로그인이 되어 있어 요청온 페이지로 다시 보냄 [url:" + returnUrl + "]");
            return "redirect:" + referer;
        }


        model.put(RETURN_URL, returnUrl);

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authencation(HttpServletRequest req, Cooker c) {
        //returnUrl이 없으면 인증 로직을 수행하지 않음!
        String returnUrl = getReturnUrl(req);

        String id = req.getParameter("id");
        String password = req.getParameter("password");

        //TODO 인증 로직 추가~

        // cooking for auth
        String authTokenSeed = createAuthTokenSeed();
        CookiePrototype authToken = c.cooking(AUTH_TOKEN, authTokenSeed).sha256(id);

        // 새로운 세션 생성
        sessionStore.createSession(authToken.getValue());

        // accessToken 발행
        createAccessToken(c);

        // 사용자 추가 정보를 세션 정보로 생성 ..
        Map<String, Object> session = sessionStore.getSession(authToken.getValue());
        session.put("_userId", id);

        logger.debug("로그인을 성공해 해당 페이지로 이동합니다(to: " + returnUrl + ")");

        return "redirect:" + returnUrl;
    }

    private String getReturnUrl(HttpServletRequest req) {
        String returnUrl = req.getParameter(RETURN_URL);
        if (!StringUtils.hasText(returnUrl)) {
            throw new RuntimeException("ReturnUrl 파라미터가 지정되지 않았습니다!");
        }
        return returnUrl;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Cooker c, HttpServletRequest req) {
        String returnUrl = getReturnUrl(req);
        CookiePrototype authToken = c.getPrototype(AUTH_TOKEN);
        CookiePrototype accessToken = c.getPrototype(ACCESS_TOKEN);

        if (authToken != null && accessToken != null) {
            sessionStore.removeSession(authToken.getValue());

            // 인증 관련 토큰 삭제
            authToken.delete();
            accessToken.delete();
            return "redirect:" + returnUrl;
        } else {
            throw new RuntimeException("ㄹㅗㄱㅡㅇㅣㄴ ㅎㅏㅈㅣ ㅇㅏㄴㅎㅇㅡㄴ ㅅㅏㅇㅛㅇㅈㅏㄱㅏ ㅇㅛㅊㅓㅇㅇㅡㄹ ㅎㅐㅆㅅㅡㅂㄴㅣㄷㅏ!");
        }
    }
}
