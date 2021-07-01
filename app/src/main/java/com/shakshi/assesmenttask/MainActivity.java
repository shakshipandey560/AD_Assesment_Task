package com.shakshi.assesmenttask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.andreilisun.swipedismissdialog.SwipeDismissDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button sucbut;
    ImageView imgs;
    TextView text;
    SwipeDismissDialog swipeDismissDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://backend-test-zypher.herokuapp.com/testData";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showDialogBox(response);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }


    private void showDialogBox(String response) {

        View dialog = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_main,null);
        swipeDismissDialog = new SwipeDismissDialog.Builder(MainActivity.this)
                .setView(dialog)
                .build()
                .show();

        text = (TextView)dialog.findViewById(R.id.texts);
        imgs = (ImageView)dialog.findViewById(R.id.img);

        try {

            JSONObject jsonObject = new JSONObject(response);

            String tit  = jsonObject.getString("title");
            text.setText(tit);

            String idi  = jsonObject.getString("imageURL");
            Picasso.get().load("https://i.pinimg.com/originals/93/46/53/934653214719cf630e0f5cf9c746b364.png").into(imgs);

            sucbut = (Button)dialog.findViewById(R.id.successButton);
            sucbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    String url = "https://www.spotify.com/in-en/home/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}