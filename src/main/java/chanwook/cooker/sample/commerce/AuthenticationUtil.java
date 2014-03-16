package chanwook.cooker.sample.commerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chanwook on 2014. 3. 6..
 */
public class AuthenticationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtil.class);

    public static final String ACCESS_TOKEN = "_accessToken";
    public static final String AUTH_TOKEN = "_authToken";
    public static final String RETURN_URL = "_returnUrl";

    public static String createAuthToken(HttpServletRequest req) {
        //TODO create real value
        String token = Long.toString(System.currentTimeMillis());
        LOGGER.debug("publishing new accessToken: " + token);
        return token;
    }

    public static String createAccessToken(HttpServletRequest req) {
        //TODO create real value
        String token = Long.toString(System.currentTimeMillis());
        LOGGER.debug("publishing new accessToken: " + token);
        return token;
    }
}
