package chanwook.cooker.sample.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chanwook on 2014. 3. 14..
 */
@Controller()
@RequestMapping(value = "/sample")
public class HttpApiOnlyCookieSampleController {

    @RequestMapping(value = "/simple")
    public String cookingForNativeApi(HttpServletResponse response) {
        response.addCookie(new Cookie("key1", "value1"));
        response.addCookie(new Cookie("key2", "value2"));
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

    @RequestMapping(value = "/api")
    public String cookingWithApi(HttpServletResponse response) {
//        response.addCookie(new Cookie("api1", "value1"));
//        response.addCookie(new Cookie("api2", "value2"));
        return "cookieSample";
    }

}
