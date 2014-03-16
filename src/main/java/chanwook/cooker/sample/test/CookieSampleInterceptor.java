package chanwook.cooker.sample.test;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by chanwook on 2014. 3. 14..
 */
public class CookieSampleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(">>>Call Cookie Interceptor!");

        Cookie cookie = new Cookie("api3", "api3");
        cookie.setPath("/");
        cookie.setDomain(".localhost");
        cookie.setMaxAge(-1);
        cookie.setVersion(0);
        cookie.setSecure(false);
        cookie.setHttpOnly(false);

        response.addCookie(cookie);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
