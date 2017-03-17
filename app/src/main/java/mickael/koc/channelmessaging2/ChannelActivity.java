package mickael.koc.channelmessaging2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import mickael.koc.channelmessaging2.FragmentPackage.ChannelListFragment;
import mickael.koc.channelmessaging2.FragmentPackage.MessageFragment;

/**
 * Created by kocm on 23/01/2017.
 */
public class ChannelActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    public static final String PREFS_NAME = "MyPrefsFile";
    public ListView lstchannel;
    public Button btnamis;
    private String[] listItems;// = {"item 1", "item 2 ", "list", "android", "item 3", "foobar", "bar", };
    private List<Channel> listChan;
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        lstchannel = (ListView)findViewById(R.id.listView);
        btnamis = (Button)findViewById(R.id.btnamis);

        btnamis.setOnClickListener(this) ;

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelListFragment fragA = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
        MessageFragment fragB = (MessageFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
        listChan=fragA.listChan;
        if(fragB == null|| !fragB.isInLayout()){

            String pos = Integer.toString(position);
            String chanID = Integer.toString(listChan.get(position).getid());
            Toast.makeText(getApplicationContext(),pos,Toast.LENGTH_SHORT).show();
            changeActivity();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("channelid", chanID); // Commit the edits! editor.commit();
            editor.commit();
        } else {

            String chanID = Integer.toString(listChan.get(position).getid());
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("channelid", chanID);
            editor.commit();
          //  fragB.fillTextView(fragA.listItems[position]);
            // fragB.filltxtview();
        }


    }



    public void changeActivity()
    {
        Intent myintent = new Intent(getApplicationContext(),MessageActivity.class);
        startActivity(myintent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnamis)
        {
            Intent myintent = new Intent(getApplicationContext(),AmisActivity.class);
            startActivity(myintent);
            Toast.makeText(getApplicationContext(),"okok",Toast.LENGTH_SHORT).show();
        }
    }
}
