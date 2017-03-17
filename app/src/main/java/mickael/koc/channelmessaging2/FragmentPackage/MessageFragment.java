package mickael.koc.channelmessaging2.FragmentPackage;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import mickael.koc.channelmessaging2.ChannelActivity;
import mickael.koc.channelmessaging2.ChannelAdapter;
import mickael.koc.channelmessaging2.Datalayer;
import mickael.koc.channelmessaging2.Message;
import mickael.koc.channelmessaging2.MessageAdapter;
import mickael.koc.channelmessaging2.Messages;
import mickael.koc.channelmessaging2.R;
import mickael.koc.channelmessaging2.UserDataSource;
import mickael.koc.channelmessaging2.onDownloadCompleteListener;

/**
 * Created by kocm on 09/03/2017.
 */
public class MessageFragment extends Fragment implements onDownloadCompleteListener, View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String PREFS_NAME = "MyPrefsFile";
    public List<Message> lstmessage;
    public ListView lstvmessage;
    public Button btnsend;
    public EditText edtmsg;
    public ImageView img;
    //CTRL+ALT+L INDENTATION AUTO
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.message_list_fragment, container); // POSSIBLY DISPLAY APPI
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation? (to avoid a never ask again response)
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        lstvmessage = (ListView) v.findViewById(R.id.listView2);
        btnsend = (Button) v.findViewById(R.id.button2);
        btnsend.setOnClickListener(this);
        img = (ImageView) v.findViewById(R.id.imageView);
        lstvmessage.setOnItemClickListener(this);
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                SharedPreferences Gsettings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                String access = Gsettings.getString("access", "");
                String channelid = Gsettings.getString("channelid", "");
                if (channelid != "") {
                    HashMap<String, String> n = new HashMap<String, String>();
                    n.put("url", "http://www.raphaelbischof.fr/messaging/?function=getmessages");
                    n.put("accesstoken", access);
                    n.put("channelid", channelid);
                    Datalayer d = new Datalayer(n);
                    d.setOnNewsDownloadComplete(new onDownloadCompleteListener() {
                        @Override
                        public void onDownloadComplete(String news, int param1) {
                            if (param1 == 5) {
                                Toast.makeText(getActivity(), "Message envoyé", Toast.LENGTH_SHORT).show();
                            } else {
                                Gson gson = new Gson();
                                Messages obj2 = gson.fromJson(news, Messages.class);
                                lstmessage = obj2.getlist();

                                int index = lstvmessage.getFirstVisiblePosition();

                                View v = lstvmessage.getChildAt(0);

                                int top = (v == null) ? 0 : (v.getTop() - lstvmessage.getPaddingTop());

                                // SET ADAPTER
                                lstvmessage.setAdapter(new MessageAdapter(getActivity(), lstmessage));
                                // restore index and position
                                lstvmessage.setSelectionFromTop(index, top);


                            }
                        }

                        @Override
                        public void onDownloadCompleteImg(String result) {

                        }
                    });
                    d.execute();
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(r, 1000);


        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {

    }

    public void filltxtview(List<Message> lstmessage) {
        lstvmessage.setAdapter(new MessageAdapter(getActivity(), lstmessage));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String pos = Integer.toString(position);
        String userID = Integer.toString(lstmessage.get(position).getUserID());
        String nameuser = lstmessage.get(position).getname();
        String imguser = lstmessage.get(position).getimg();
        Toast.makeText(getActivity(), userID + " " + nameuser + "  " + imguser, Toast.LENGTH_SHORT).show();
        UserDataSource u = new UserDataSource(getActivity());

        try {
            u.open();
            u.createUser(lstmessage.get(position).getUserID(), nameuser, imguser);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDownloadComplete(String news, int param1) {

    }

    @Override
    public void onDownloadCompleteImg(String result) {

    }
    //CTRL+ALT+L INDENTATION AUTO
}
