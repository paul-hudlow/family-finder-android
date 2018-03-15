package android.familyfinder.hudlow.com.familyfinder;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

public abstract class GenericTask<T> extends AsyncTask<String, Integer, T> {

    private final Callback<T> callback;

    public GenericTask(Callback<T> callback) {
        this.callback = callback;
    }

    @Override
    protected T doInBackground(String... strings) {
        return doInBackground();
    }

    @Override
    protected void onPostExecute(T result) {
        callback.execute(result);
    }

    protected abstract T doInBackground();

}
