package ifwe.twittersearch;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetAdapter extends ArrayAdapter<com.twitter.sdk.android.core.models.Tweet> {


private Context context;

    public interface IActionClickListener{
        void onActionClicked(int position, String action);
    }

    private IActionClickListener actionClickListener;
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvCreatedTime;
        public TextView tvScreenName;
        public TextView tvRetweets;
        public TextView tvlikes;
        public TextView tvRetweeted;
        public ImageView ivReply;
    }

    public TweetAdapter(Context context, List<com.twitter.sdk.android.core.models.Tweet> tweets, IActionClickListener actionClickListener) {
        super(context, 0, tweets);
        this.context = context;
        this.actionClickListener = actionClickListener;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final com.twitter.sdk.android.core.models.Tweet tweet = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_tweet, parent, false);
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.tvBody = (TextView)convertView.findViewById(R.id.tvBody);
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
            viewHolder.tvCreatedTime = (TextView)convertView.findViewById(R.id.tvCreatedTime);
            viewHolder.tvScreenName = (TextView)convertView.findViewById(R.id.tvScreeName);
            viewHolder.tvlikes = (TextView)convertView.findViewById(R.id.tvlikes);
            viewHolder.tvRetweets = (TextView)convertView.findViewById(R.id.tvRetweets);
            viewHolder.tvRetweeted = (TextView)convertView.findViewById(R.id.tvRetweeted);
            viewHolder.ivReply = (ImageView)convertView.findViewById(R.id.ivReply);
            convertView.setTag(viewHolder);
            
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvBody.setText(tweet.text);
        viewHolder.tvUserName.setText(tweet.user.name);
        viewHolder.tvScreenName.setText(tweet.user.screenName);
        viewHolder.tvCreatedTime.setText(Utils.getRelativeTimeAgo((tweet.createdAt)));
        if(tweet.retweetedStatus != null && tweet.retweetedStatus.user!= null){
            viewHolder.tvRetweeted.setText(tweet.retweetedStatus.user.name + " retweeted");
            viewHolder.tvRetweeted.setVisibility(View.VISIBLE);

        }
        else { 
            viewHolder.tvRetweeted.setVisibility(View.GONE);

        }
        if(tweet.favoriteCount != null && tweet.favoriteCount > 0) {
            viewHolder.tvlikes.setText(tweet.favoriteCount + "");
        }else{
            viewHolder.tvlikes.setText("");
        }
        
        if(tweet.retweetCount > 0) {
            viewHolder.tvRetweets.setText(tweet.retweetCount + "");
        }else{
            viewHolder.tvRetweets.setText("");
        }
        
        viewHolder.ivImage.setImageResource(0);

        Picasso.with(getContext())
                .load(Uri.parse(tweet.user.profileImageUrl))
                .fit().centerCrop()
                .into(viewHolder.ivImage);


        return convertView;
    }


}
