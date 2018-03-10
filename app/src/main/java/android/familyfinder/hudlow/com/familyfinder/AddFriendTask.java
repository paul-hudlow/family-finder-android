package android.familyfinder.hudlow.com.familyfinder;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AddFriendTask extends AsyncTask<String, Integer, Boolean> {

    private URL endpoint;
    private String myUserId;
    private String friendUserId;

    AddFriendTask(String serverUrl, String myUserId, String friendUserId) throws MalformedURLException {
        endpoint = new URL(serverUrl + "/add");
        this.myUserId = myUserId;
        this.friendUserId = friendUserId;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return makeRequest(myUserId, friendUserId);
    }

    private Boolean makeRequest(String myUserId, String friendUserId) {
        String json = String.format(
                "{ \"myUserId\": \"%s\", \"friendUserId\": \"%s\" }",
                myUserId, friendUserId);
        OutputStream requestStream = null;
        InputStream responseStream = null;
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) endpoint.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
            connection.connect();

            requestStream = connection.getOutputStream();
            requestStream.write(json.getBytes());

            responseStream = connection.getInputStream();
            String response = readStream(responseStream, 500);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode responseJson = mapper.readValue(response, ObjectNode.class);
            if (responseJson.get("success").asBoolean()) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {
            return false;
        } finally {
            if (requestStream != null) {
                try {
                    requestStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (responseStream != null) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}
