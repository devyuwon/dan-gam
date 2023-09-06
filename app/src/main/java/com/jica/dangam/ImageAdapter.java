package com.jica.dangam;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
	private ArrayList<Uri> mData = null;
	private Context mContext = null;

	ImageAdapter(ArrayList<Uri> list, Context context) {
		mData = list;
		mContext = context;
	}

	@NonNull
	@Override
	public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.recyclerview_post_write_image, parent, false);
		ImageAdapter.ViewHolder vh = new ImageAdapter.ViewHolder(view);

		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
		Uri img_uri = mData.get(position);
		Glide.with(mContext)
			.load(img_uri)
			.into(holder.image);
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView image;

		ViewHolder(View itemView) {
			super(itemView);
			image = itemView.findViewById(R.id.post_write_recyclerview_image);
		}
	}
}
