package kr.co.thatzit.cinderellachat;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by LSJ on 2015-12-16.
 */
public class ServerCallMessage {

    private Context context;

    public ServerCallMessage(Context context) {
        this.context = context;
    }

    private final String SERVER_URL = "http://192.168.0.20:52273/gcm";

    public boolean sendMessage() {
        boolean isSuccess = false;
        String token = getRegistrationID();
        HttpURLConnection conn = null;
        try {
            String body = "gcm_id=" + token;
            URL url = new URL(SERVER_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            JSONObject job = new JSONObject();
            job.put("gcm_id", token);
            OutputStream os;
            os = conn.getOutputStream();
            os.write(job.toString().getBytes());
            os.flush();
            int responseCode = conn.getResponseCode();
            Log.i("asdasd", "response code = " + responseCode);
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return isSuccess;
    }

    private String getRegistrationID() {
        SharedPreferences pref = context.getSharedPreferences("GCM", context.MODE_PRIVATE);
        return pref.getString("registrationID", null);
    }
}
