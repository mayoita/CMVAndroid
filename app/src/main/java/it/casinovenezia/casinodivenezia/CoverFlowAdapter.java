package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class CoverFlowAdapter extends BaseAdapter {
	
	private ArrayList<EventItem> mData = new ArrayList<>(0);
	private Context mContext;
	FirebaseStorage storage = FirebaseStorage.getInstance();
	StorageReference storageRef = storage.getReferenceFromUrl("gs://cmv-gioco.appspot.com/Events");

	public CoverFlowAdapter(Context context) {
		mContext = context;
	}
	
	public void setData(ArrayList<EventItem> data) {

		mData = data;
	}
	
	@Override
	public int getCount() {
		return
				mData.size();
	}

	@Override
	public Object getItem(int pos) {
		return mData.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
		StorageReference imagesRef = storageRef.child(mData.get(position).getImageMain());
		imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
			@Override
			public void onSuccess(Uri uri) {
				Picasso.with(mContext).load(uri.toString()).placeholder(R.drawable.default_event).into(holder.image);
			}
		});

        holder.text.setText(mData.get(position).getNameIT());

		return rowView;
	}


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}
