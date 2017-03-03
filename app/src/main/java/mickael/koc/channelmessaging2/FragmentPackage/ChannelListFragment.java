/*package mickael.koc.channelmessaging2.FragmentPackage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import mickael.koc.channelmessaging2.R;


public class ChannelListFragment extends Fragment{
    public ListView lstchannel;
    public Button btnamis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.,container);
        lstchannel = (ListView)v.findViewById(R.id.lvFragment);
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstchannel.setAdapter(new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, listItems));
        lstchannel.setOnItemClickListener((MainActivity)getActivity());
    }
}

*/