package chanwook.cooker.sample.test;

import chanwook.cooker.Cooker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chanwook on 2014. 3. 14..
 */
@Controller()
@RequestMapping(value = "/cooker")
public class CookerApiCookieSampleController {

    @RequestMapping(value = "/simple")
    public String cookingForNativeApi(Cooker c) {
        c.cooking(new Cookie("key11", "value11"));
        c.cooking(new Cookie("key12", "value12"));
        return "cookieSample";
    }

    @RequestMapping(value = "/redirectFrom")
    public String redirectFrom(HttpServletResponse response) {
        response.addCookie(new Cookie("key3", "value3"));
        return "redirect:/sample/redirectTo";
    }

    @RequestMapping(value = "/redirectTo")
    public String redirectTo() {
        return "cookieSample";
    }
}
