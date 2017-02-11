package in.madscientist.chattest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.madscientist.chattest.R;
import in.madscientist.chattest.model.pojo.User;

/**
 * Created by frapp on 11/2/17.
 */

public class AdapterUserDetails extends RecyclerView.Adapter<AdapterUserDetails.ViewHolder> {

    private Context context;
    private List<User>users;

    public AdapterUserDetails() {
    }

    public AdapterUserDetails(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_user_details,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        User user = users.get(position);
        if (!TextUtils.isEmpty(user.getImage_url())){
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.circleImageView.setImageBitmap(bitmap);
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int mutedColor = palette.getVibrantColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            holder.unameTV.setTextColor(Color.parseColor("#" + Integer.toHexString(mutedColor).substring(2)));
                        }
                    });
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    holder.circleImageView.setImageDrawable(placeHolderDrawable);
                }
            };

            holder.circleImageView.setTag(target);

            Picasso.with(context)
                    .load(user.getImage_url())
                    .placeholder(ContextCompat.getDrawable(context, android.R.drawable.ic_dialog_alert))
                    .into(target);
        }else
        {
            Picasso.with(context).load(android.R.drawable.ic_dialog_alert).fit().into(holder.circleImageView);
        }
        holder.unameTV.setText(user.getName());
        holder.favMsgCountTV.setText(": "+String.valueOf(user.getFavCount()));
        holder.totalMsgCountTV.setText(context.getString(R.string.total_msg_filler,user.getTotalMsgCount()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rud_favsMsgCount)TextView favMsgCountTV;
        @BindView(R.id.rud_totalMsgs)TextView totalMsgCountTV;
        @BindView(R.id.rud_uname)TextView unameTV;
        @BindView(R.id.rud_CIV)CircleImageView circleImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
