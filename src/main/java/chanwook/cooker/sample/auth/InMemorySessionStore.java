package chanwook.cooker.sample.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chanwook on 2014. 3. 17..
 */
public class InMemorySessionStore implements SessionStore {

    private Map<String, HashMap<String, Object>> sessionMap = new HashMap<String, HashMap<String, Object>>();

    @Override
    public void createSession(String sessionId) {
        sessionMap.put(sessionId, new HashMap<String, Object>());
    }

    @Override
    public void addSessionAttribute(String sessionId, String key, String value) {
        //TODO ㅇㅖㅇㅗㅣ ㅊㅓㄹㅣ
        HashMap<String, Object> session = sessionMap.get(sessionId);
        session.put(key, value);
    }

    //TODO 타입 생성
    @Override
    public Map<String, Object> getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    @Override
    public Object getSessionAttribute(String sessionId, String key) {
        //TODO 예외 처리
        return sessionMap.get(sessionId).get(key);
    }

    @Override
    public void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }
}
