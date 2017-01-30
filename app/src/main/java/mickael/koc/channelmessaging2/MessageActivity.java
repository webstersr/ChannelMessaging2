package mickael.koc.channelmessaging2;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kocm on 27/01/2017.
 */
public class MessageActivity extends AppCompatActivity implements onDownloadCompleteListener, View.OnClickListener{

    public static final String PREFS_NAME = "MyPrefsFile";
    private List<Message> lstmessage;
    public ListView lstvmessage;
    public Button btnsend;
    public EditText edtmsg;
    public ImageView img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        lstvmessage = (ListView)findViewById(R.id.listView2);
        btnsend = (Button)findViewById(R.id.button2);
        btnsend.setOnClickListener(this);
        img = (ImageView)findViewById(R.id.imageView);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
                String access = Gsettings.getString("access", "");
                String channelid = Gsettings.getString("channelid","");
                HashMap<String,String> n = new HashMap<String,String>();
                n.put("url","http://www.raphaelbischof.fr/messaging/?function=getmessages");
                n.put("accesstoken",access);
                n.put("channelid",channelid);
                Datalayer d = new Datalayer(n);
                d.setOnNewsDownloadComplete(MessageActivity.this);
                d.execute();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);
    }

    @Override
    public void onDownloadComplete(String news, int param2) {
        if(param2==5)
        {
            Toast.makeText(getApplicationContext(),"Message envoyé",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Gson gson = new Gson();
            Messages obj2 = gson.fromJson(news, Messages.class);
            lstmessage=obj2.getlist();

            int index = lstvmessage.getFirstVisiblePosition();

            View v = lstvmessage.getChildAt(0);

            int top = (v == null) ? 0 : (v.getTop() - lstvmessage.getPaddingTop());

// SET ADAPTER
            lstvmessage.setAdapter(new MessageAdapter(getApplicationContext(),lstmessage));
// restore index and position

            lstvmessage.setSelectionFromTop(index, top);


        }

    }

    @Override
    public void onDownloadCompleteImg(String result) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button2)
        {
            edtmsg=(EditText) findViewById(R.id.editText);
            SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
            String access = Gsettings.getString("access", "");
            String channelid = Gsettings.getString("channelid","");
            HashMap<String,String> n = new HashMap<String,String>();
            n.put("url","http://www.raphaelbischof.fr/messaging/?function=sendmessage");
            n.put("accesstoken",access);
            n.put("channelid",channelid);
            n.put("message",edtmsg.getText().toString());//
            Datalayer d = new Datalayer(n,0,5);
            d.setOnNewsDownloadComplete(MessageActivity.this);
            d.execute();
        }
    }
}
