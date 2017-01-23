package mickael.koc.channelmessaging2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements onDownloadCompleteListener, View.OnClickListener {

    private Button btnvalider;
    private EditText edtlogin;
    private EditText edtpassword;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String message = "";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnvalider = (Button) findViewById(R.id.button);
        edtlogin = (EditText) findViewById(R.id.edtlogin);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        btnvalider.setOnClickListener(this) ;
    }

    @Override
    public void onDownloadComplete(String news) {
       //MARCHE :
        // Toast.makeText(getApplicationContext(),news,Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        Jsonlogin obj2 = gson.fromJson(news, Jsonlogin.class);

        if(obj2.code==200) {
            //================SHARED PREFERENCE (LIKE $_SESSION)
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("access", obj2.getaccess()); // Commit the edits! editor.commit();
            editor.commit();
            //===================================
            //================GET PREFERENCE
            SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
            String access = Gsettings.getString("access", "");
            //===================================
            changeActivity();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Identifiant incorrect", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button:
                    Datalayer d = new Datalayer(edtlogin.getText().toString(),edtpassword.getText().toString());
                    //Datalayer d = new Datalayer("mkoc","mickaelkoc");
                    d.setOnNewsDownloadComplete(this);
                    d.execute();

                break;


        }


    }

    public void changeActivity()
    {
        Intent myintent = new Intent(getApplicationContext(),ChannelActivity.class);
        startActivity(myintent);
    }

}
