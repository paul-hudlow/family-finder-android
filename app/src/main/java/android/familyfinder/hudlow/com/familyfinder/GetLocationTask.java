package android.familyfinder.hudlow.com.familyfinder;

import com.google.android.gms.maps.model.LatLng;
import com.hudlow.familyfinder.client.CommunicationException;
import com.hudlow.familyfinder.client.GetLocationCall;

public class GetLocationTask extends GenericTask<LatLng> {

    private String myUserId;
    private String friendUserId;

    GetLocationTask(String myUserId, String friendUserId, Callback<LatLng> callback) {
        super(callback);
        this.myUserId = myUserId;
        this.friendUserId = friendUserId;
    }

    @Override
    protected LatLng doInBackground() {
        GetLocationCall serverCall = new GetLocationCall();
        GetLocationCall.LocationResponse response = null;
        try {
            response = serverCall.getLocation(myUserId, friendUserId);
        } catch (CommunicationException e) {
            return null;
        }
        return new LatLng(response.latitude, response.longitude);
    }

}
