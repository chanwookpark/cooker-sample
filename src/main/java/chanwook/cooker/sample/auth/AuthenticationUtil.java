package chanwook.cooker.sample.auth;

import chanwook.cooker.Cooker;
import chanwook.cooker.CookiePrototype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 3. 6..
 */
public class AuthenticationUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationUtil.class);

    public static final String ACCESS_TOKEN = "_accessToken";
    public static final String AUTH_TOKEN = "_authToken";
    public static final String RETURN_URL = "_returnUrl";

    //TODO refactoring..
    private static final Map<String, String> tokenMap = new HashMap<String, String>();

    //TODO refactoring..
    private static SessionStore sessionStore = new InMemorySessionStore();

    public static String createAuthTokenSeed() {
        //TODO create real value
        String token = Long.toString(System.currentTimeMillis());
        LOGGER.debug("publishing new accessToken: " + token);
        return token;
    }

    public static String createAccessTokenSeed() {
        //TODO create real value
        String accessToken = Long.toString(System.currentTimeMillis());
        LOGGER.debug("publishing new accessToken: " + accessToken);
        return accessToken;
    }

    public static boolean isValidAccessToken(String authToken, String accessToken) {
        Object savedToken = sessionStore.getSessionAttribute(authToken, ACCESS_TOKEN);
        if (savedToken != null && savedToken.equals(accessToken)) {
            return true;
        }
        return false;
    }

    public static SessionStore getSessionStore() {
        return sessionStore;
    }

    public static String createAccessToken(Cooker cooker) {
        CookiePrototype accessToken = cooker.cooking(ACCESS_TOKEN, createAccessTokenSeed()).encoding();
        String authToken = cooker.getCookieValue(AUTH_TOKEN);
        sessionStore.addSessionAttribute(authToken, ACCESS_TOKEN, accessToken.getValue());
        return accessToken.getValue();
    }
}
