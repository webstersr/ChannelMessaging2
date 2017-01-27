package mickael.koc.channelmessaging2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by kocm on 27/01/2017.
 */
public class MessageActivity extends AppCompatActivity implements onDownloadCompleteListener{

    public static final String PREFS_NAME = "MyPrefsFile";
    private List<Message> lstmessage;
    public ListView lstvmessage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        lstvmessage = (ListView)findViewById(R.id.listView2);
        SharedPreferences Gsettings = getSharedPreferences(PREFS_NAME, 0);
        String access = Gsettings.getString("access", "");
        String channelid = Gsettings.getString("channelid","");
        HashMap<String,String> n = new HashMap<String,String>();
        n.put("url","http://www.raphaelbischof.fr/messaging/?function=getmessages");
        n.put("accesstoken",access);
        n.put("channelid",channelid);
        Datalayer d = new Datalayer(n);
        d.setOnNewsDownloadComplete(this);
        d.execute();

    }

    @Override
    public void onDownloadComplete(String news) {
        Toast.makeText(getApplicationContext(),news,Toast.LENGTH_SHORT).show();
        Gson gson = new Gson();
        Messages obj2 = gson.fromJson(news, Messages.class);
        lstmessage=obj2.getlist();
        lstvmessage.setAdapter(new MessageAdapter(getApplicationContext(),lstmessage));
    }
}
