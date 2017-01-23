package mickael.koc.channelmessaging2;

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
        Toast.makeText(getApplicationContext(),obj2.getaccess(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button:
                    Datalayer d = new Datalayer(edtlogin.getText().toString(),edtpassword.getText().toString());

                    d.setOnNewsDownloadComplete(this);
                    d.execute();
                break;


        }


    }
}
