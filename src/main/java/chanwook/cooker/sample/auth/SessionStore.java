package chanwook.cooker.sample.auth;

import java.util.Map;

/**
 * 서버 세션 저장소..
 * <p/>
 * Created by chanwook on 2014. 3. 17..
 */
public interface SessionStore {

    void createSession(String sessionId);

    void addSessionAttribute(String sessionId, String key, String value);

    Map<String, Object> getSession(String sessionId);

    Object getSessionAttribute(String sessionId, String key);

    void removeSession(String sessionId);
}
