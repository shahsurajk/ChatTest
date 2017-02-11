package in.madscientist.chattest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.madscientist.chattest.R;
import in.madscientist.chattest.adapter.AdapterUserDetails;
import in.madscientist.chattest.db.DBOperations;
import in.madscientist.chattest.db.DatabaseManager;
import in.madscientist.chattest.model.pojo.User;

/**
 * Created by frapp on 11/2/17.
 */

public class Fragment_UserDetails extends Fragment {

    private List<User>users=new ArrayList<>();
    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    private AdapterUserDetails adapterUserDetails;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats,container,false);
    }

    public static final String TAG = Fragment_UserDetails.class.getCanonicalName().toString();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapterUserDetails = new AdapterUserDetails(getActivity(),users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterUserDetails);
        refreshList();
    }

    public void refreshList()
    {
        users.clear();
        users.addAll(DBOperations.getUsersFromDB(DatabaseManager.getInstance().openDatabase()));
        DatabaseManager.getInstance().closeDatabase();
        if (adapterUserDetails!=null)
        adapterUserDetails.notifyDataSetChanged();
    }

}
