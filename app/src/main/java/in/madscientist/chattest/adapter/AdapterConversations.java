package in.madscientist.chattest.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.madscientist.chattest.R;
import in.madscientist.chattest.db.DBOperations;
import in.madscientist.chattest.db.DatabaseManager;
import in.madscientist.chattest.model.pojo.Message;
import in.madscientist.chattest.model.pojo.User;
import in.madscientist.chattest.utils.Utils;

/**
 * Created by frapp on 11/2/17.
 */

public class AdapterConversations extends RecyclerView.Adapter<AdapterConversations.ViewHolder> {
    private static final int CHAT_ITEM_LEFT = 0;
    private static final int CHAT_ITEM_RIGHT = 1;
    private static final String FAV_ICON_FILLED ="\uf004";
    private static final String FAV_ICON_NON_FILLED ="\uf08a";

    private Context context;
    private List<Message>messages;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==CHAT_ITEM_LEFT)
        {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_chat_left,parent,false));
        }else
        {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_chat_right,parent,false));
        }
    }

    public AdapterConversations(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    public AdapterConversations() {
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Message message = messages.get(position);
        holder.chatText.setText(message.getBody());
        holder.unanme.setText(message.getName());
        holder.timestamp.setText(Utils.parseDate(message.getMessage_time()));
        String iconToSet;
        int colorToSet ;
        if (messages.get(position).getIsFavourite()== User.MESSAGE_TYPE_FAV)
        {
            iconToSet = FAV_ICON_FILLED;
            colorToSet=R.color.THEME_RED;
        }else
        {
            colorToSet = R.color.THEME_TEXT_COLOR;
            iconToSet = FAV_ICON_NON_FILLED;
        }
        holder.favBtn.setText(iconToSet);
        holder.favBtn.setTextColor(ContextCompat.getColor(context,colorToSet));
        if (!TextUtils.isEmpty(message.getImage_url()))
        Picasso.with(context).load(message.getImage_url()).fit().placeholder(android.R.drawable.ic_dialog_alert).into(holder.icon);
        else
        {
            Picasso.with(context).load(android.R.drawable.ic_dialog_alert).into(holder.icon);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavSelection(position,holder);
               }
        });
    }

    private void toggleFavSelection(int position, ViewHolder holder)
    {
        int typeToSet;
        int colorToSet;
        String iconToSet;
        if (messages.get(position).getIsFavourite()== User.MESSAGE_TYPE_FAV)
        {
            colorToSet = R.color.THEME_TEXT_COLOR;
            typeToSet = User.MESSAGE_TYPE_NOT_FAV;
            iconToSet = FAV_ICON_NON_FILLED;
        }else
        {
            colorToSet= R.color.THEME_RED;
            iconToSet = FAV_ICON_FILLED;
            typeToSet = User.MESSAGE_TYPE_FAV;
        }
        holder.favBtn.setText(iconToSet);
        holder.favBtn.setTextColor(ContextCompat.getColor(context,colorToSet));
        DBOperations.setMessageToFav(DatabaseManager.getInstance().openDatabase(),messages.get(position),typeToSet);
        DatabaseManager.getInstance().closeDatabase();
        messages.get(position).setIsFavourite(typeToSet);
    }
    @Override
    public int getItemViewType(int position) {
        if (position!=0)
        {
            if (messages.get(position).getUsername().equalsIgnoreCase(messages.get(position-1).getUsername()))
            {
                return getItemViewType(position-1);
            }
        }

        if (position%2==0)
        {
            return CHAT_ITEM_LEFT;
        }else
        {
            return CHAT_ITEM_RIGHT;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lic_text)
        TextView chatText;
        @BindView(R.id.lic_icon)
        CircleImageView icon;
        @BindView(R.id.lic_timestamp)TextView timestamp;
        @BindView(R.id.lic_uname)TextView unanme;
        @BindView(R.id.lic_fav)TextView favBtn;

        @BindView(R.id.lic_card)CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
