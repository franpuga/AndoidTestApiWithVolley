package volley.javacodegeeks.com.androidvolleyexample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private TextView txtShowTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Formato de la respuesta, intentado en local no funciona es necesario que esté el servicio en "INTERNET"
        /*{
            "response":[
            {
                "User" : "fjpuga@aldaba.es",
                    "Password" : "Fys=1603"
            }
    ]
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtShowTextResult = findViewById(R.id.txtDisplay);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "http://www.mocky.io/v2/5c190b4c2f00005f00af13a0";//"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyBrJ3ec9wTuS6L-xHkaXLU8BJbFsx_LZ9o";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response.getJSONArray("response");
                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        formattedResult.append("\n" + responseJSONArray.getJSONObject(i).get("User") + "=> \t" + responseJSONArray.getJSONObject(i).get("Password"));
                    }
                    txtShowTextResult.setText("List of Users \n" + " User" + "\t Password \n" + formattedResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtShowTextResult.setText("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
