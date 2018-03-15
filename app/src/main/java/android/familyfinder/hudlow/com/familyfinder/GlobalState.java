package android.familyfinder.hudlow.com.familyfinder;

import java.util.ArrayList;
import java.util.List;

public class GlobalState {

    private static GlobalState instance;

    private final List<String> friendList = new ArrayList<String>();

    public static GlobalState getInstance() {
        if (instance == null) {
            instance = new GlobalState();
        }
        return instance;
    }

    public String getUserId() {
        return "paul";
    }

    public List<String> getFriendList() {
        return friendList;
    }
}
