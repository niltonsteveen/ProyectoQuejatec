package co.edu.udea.quejatec.Clases;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class EnviaNotificacion {

    public EnviaNotificacion(String tokenTo, String title, String body) {
        EnviaNoti enviaNoti = new EnviaNoti();
        enviaNoti.execute(
                title,
                body,
                tokenTo
        );

    }

    private class EnviaNoti extends AsyncTask<String, Void, Void> {
        private String TAG = "TEST";

        @Override
        protected Void doInBackground(String... params){

            Log.i(TAG,"doInBackgound");
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
            post.addHeader("content-type","application/json");
            post.addHeader("authorization","key=AAAAtDsrnr4:APA91bGsikFUka0KuSOeE-zUCgRDgj2TIYQoj1HhyWLxye8O7kKu3i55IPvqncDXlL7wbfFb74ctscYYvkw6Q_HXgJYD-YNbmr9PbOYqwgw8z1My6LGHsi1w0mQnAE8UFa0i5nwqbsMo");

            try{

                String json = "";
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.accumulate("title","Notificación: "+params[0]);
                jsonObject2.accumulate("body","Evento: "+params[1]);
                jsonObject.accumulate("to",params[2]);
                jsonObject.accumulate("notification",jsonObject2);
                json = jsonObject.toString();
                Log.d(TAG,json);
                StringEntity stringEntity = new StringEntity(json);
                post.setEntity((stringEntity));

                HttpResponse resp = httpClient.execute(post);
                String respStr = EntityUtils.toString(resp.getEntity());

                Log.d(TAG,respStr);
            }
            catch (Exception ex)
            {
                Log.e("ServicioRest","Error!",ex);
                ex.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void result){

        }

        @Override
        protected void onPreExecute(){
            Log.i(TAG,"onPreExecute");

        }

    }
}
