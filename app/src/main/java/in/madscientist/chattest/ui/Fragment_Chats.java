package in.madscientist.chattest.ui;

import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.madscientist.chattest.R;
import in.madscientist.chattest.adapter.AdapterConversations;
import in.madscientist.chattest.db.DBOperations;
import in.madscientist.chattest.db.DatabaseManager;
import in.madscientist.chattest.model.pojo.Chats;
import in.madscientist.chattest.model.pojo.Message;
import in.madscientist.chattest.utils.Utils;
import in.madscientist.chattest.network.JacksonRequest;
import in.madscientist.chattest.network.URL;
import in.madscientist.chattest.network.VolleyManager;

/**
 * Created by frapp on 11/2/17.
 */

public class Fragment_Chats extends Fragment {

    @BindView(R.id.recyclerView)RecyclerView recyclerView;
    @BindView(R.id.progressBar)ProgressBar progressBar;
    private AdapterConversations adapterConversations;
    List<Message>messages= new ArrayList<>();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapterConversations = new AdapterConversations(getActivity(),messages);
        recyclerView.setAdapter(adapterConversations);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        getAllChats();
    }
    private void getAllChats()
    {
        if (isAdded() && Utils.isOnline(getActivity()))
        {
            progressBar.setVisibility(View.VISIBLE);
            JacksonRequest jacksonRequest = new JacksonRequest(Request.Method.GET, URL.CHAT_URL, null, Chats.class,
                    new Response.Listener() {
                        @Override
                        public void onResponse(Object o) {
                            progressBar.setVisibility(View.GONE);
                            Chats chats = (Chats) o;
                            SQLiteDatabase sqLiteDatabase=DatabaseManager.getInstance().openDatabase();
                            boolean isUpdated = DBOperations.checkIfListIsUpdated(sqLiteDatabase,
                                    chats.getMessages().get(chats.getMessages().size()-1).getMessage_time());
                            Log.i("frag", "onResponse: "+isUpdated+" "+chats.getMessages().size());
                            if(!isUpdated){
                                int start =0;
                                int checkedIndex = getIndexForTimeStamp(sqLiteDatabase,
                                        chats.getMessages().get(chats.getMessages().size()-1).getMessage_time());
                                if (checkedIndex!=-1)
                                {
                                    start=checkedIndex;
                                }
                                for (int i = start; i < chats.getMessages().size(); i++) {
                                    DBOperations.enterMessageInConversation(sqLiteDatabase,chats.getMessages().get(i));
                                    messages.add(chats.getMessages().get(i));
                                }
//                                messages.addAll(chats.getMessages());
                            }else
                            {
                                messages.addAll(DBOperations.getAllMessages(sqLiteDatabase));
                            }
                            DatabaseManager.getInstance().closeDatabase();
                            adapterConversations.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if (isAdded()){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),volleyError.getLocalizedMessage(),Toast.LENGTH_LONG);
                    }
                }
            });
            VolleyManager.getInstance().addToRequestQueue(jacksonRequest);
        }else if (isAdded())
        {
            Utils.getNoInternetButtonDialog(getActivity(), true, new MaterialDialog.ButtonCallback() {
                @Override
                public void onAny(MaterialDialog dialog) {
                    super.onAny(dialog);
                    getAllChats();
                }
            }).show();
        }
    }
    private int getIndexForTimeStamp(SQLiteDatabase database, String timestampToCheck)
    {
        int index =-1;
        List<Message>dbMessages =new ArrayList<>();
        dbMessages.addAll(DBOperations.getAllMessages(database));
        if (dbMessages.size()>0)
        {
            for (int i = 0; i < dbMessages.size(); i++) {
                if (dbMessages.get(i).getMessage_time().equalsIgnoreCase(timestampToCheck))
                {
                    return i;
                }
            }
        }
        return index;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats,container,false);
    }
}
