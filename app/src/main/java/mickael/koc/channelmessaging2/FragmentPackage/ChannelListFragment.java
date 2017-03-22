package mickael.koc.channelmessaging2.FragmentPackage;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import mickael.koc.channelmessaging2.Channel;
import mickael.koc.channelmessaging2.ChannelActivity;
import mickael.koc.channelmessaging2.ChannelAdapter;
import mickael.koc.channelmessaging2.Channels;
import mickael.koc.channelmessaging2.Datalayer;
import mickael.koc.channelmessaging2.R;
import mickael.koc.channelmessaging2.onDownloadCompleteListener;


public class ChannelListFragment extends Fragment implements onDownloadCompleteListener,AdapterView.OnItemClickListener,View.OnClickListener {
    public static final String PREFS_NAME = "MyPrefsFile";
    public ListView lstchannel;
    public Button btnamis;
    public List<Channel> listChan;
    public Location loc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.chanel_list_fragment,container);
        lstchannel = (ListView)v.findViewById(R.id.listView);
        btnamis = (Button)v.findViewById(R.id.btnamis);

        SharedPreferences Gsettings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String access = Gsettings.getString("access", "");
        HashMap<String,String> n = new HashMap<String,String>();
        n.put("url","http://www.raphaelbischof.fr/messaging/?function=getchannels");
        n.put("accesstoken",access);
        Datalayer d = new Datalayer(n);
        d.setOnNewsDownloadComplete(this);
        btnamis.setOnClickListener(this) ;
        d.execute();
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lstchannel.setOnItemClickListener((ChannelActivity)getActivity());
    }


    @Override
    public void onDownloadComplete(String news, int param2) {
        Gson gson = new Gson();
        Channels obj2 = gson.fromJson(news, Channels.class);
        obj2.toString();
        listChan=obj2.getChannels();
        lstchannel.setAdapter(new ChannelAdapter(getActivity(),obj2.getChannels()));
        Toast.makeText(getActivity(), news, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadCompleteImg(String result) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

