package demo.twittersearch.adapters;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import demo.twittersearch.R;
import demo.twittersearch.Utils;

public class TweetRecyclerViewAdapter extends RecyclerView.Adapter<TweetRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onClicked(int position);
    }

    private OnItemClickListener listener;
    private List<Tweet> mTweetArrayList;
    private Context mContext;
    public TweetRecyclerViewAdapter(Context context, ArrayList<Tweet> tweets){
        mContext = context;
        mTweetArrayList = tweets;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener){
        listener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweetArrayList.get(position);
        holder.tvBody.setText(tweet.text);
        holder.tvUserName.setText(tweet.user.name);
        holder.tvScreenName.setText(tweet.user.screenName);
        holder.tvCreatedTime.setText(Utils.getRelativeTimeAgo((tweet.createdAt)));
        if(tweet.retweetedStatus != null && tweet.retweetedStatus.user!= null){
            holder.tvRetweeted.setText(tweet.retweetedStatus.user.name + " retweeted");
            holder.tvRetweeted.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvRetweeted.setVisibility(View.GONE);

        }
        if(tweet.favoriteCount != null && tweet.favoriteCount > 0) {
            holder.tvlikes.setText(tweet.favoriteCount + "");
        }else{
            holder.tvlikes.setText("");
        }

        if(tweet.retweetCount > 0) {
            holder.tvRetweets.setText(tweet.retweetCount + "");
        }else{
            holder.tvRetweets.setText("");
        }

        holder.ivImage.setImageResource(0);

        Picasso.with(mContext)
                .load(Uri.parse(tweet.user.profileImageUrl))
                .fit().centerCrop()
                .into(holder.ivImage);
    }


    @Override
    public int getItemCount() {
        if (mTweetArrayList == null) return 0;

        return mTweetArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvCreatedTime;
        public TextView tvScreenName;
        public TextView tvRetweets;
        public TextView tvlikes;
        public TextView tvRetweeted;
        public ImageView ivReply;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView)itemView.findViewById(R.id.ivImage);
            tvBody = (TextView)itemView.findViewById(R.id.tvBody);
            tvUserName = (TextView)itemView.findViewById(R.id.tvUserName);
            tvCreatedTime = (TextView)itemView.findViewById(R.id.tvCreatedTime);
            tvScreenName = (TextView)itemView.findViewById(R.id.tvScreeName);
            tvlikes = (TextView)itemView.findViewById(R.id.tvlikes);
            tvRetweets = (TextView)itemView.findViewById(R.id.tvRetweets);
            tvRetweeted = (TextView)itemView.findViewById(R.id.tvRetweeted);
            ivReply = (ImageView)itemView.findViewById(R.id.ivReply);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClicked(getAdapterPosition());
                }
            });
        }
    }
}
