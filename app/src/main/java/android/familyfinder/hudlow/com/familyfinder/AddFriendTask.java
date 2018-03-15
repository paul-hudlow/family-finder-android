package android.familyfinder.hudlow.com.familyfinder;

import com.hudlow.familyfinder.client.AddFriendCall;
import com.hudlow.familyfinder.client.CommunicationException;

public class AddFriendTask extends GenericTask<Boolean> {
    private String myUserId;
    private String friendUserId;

    AddFriendTask(String myUserId, String friendUserId, Callback<Boolean> callback) {
        super(callback);
        this.myUserId = myUserId;
        this.friendUserId = friendUserId;
    }

    @Override
    protected Boolean doInBackground() {
        try {
            AddFriendCall serverCall = new AddFriendCall();
            serverCall.addFriend(myUserId, friendUserId);
            return true;
        } catch (CommunicationException ex) {
            return false;
        }
    }


}
