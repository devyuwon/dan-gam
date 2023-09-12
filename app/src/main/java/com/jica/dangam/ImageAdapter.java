package com.jica.dangam;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
	private ArrayList<Uri> mData = null;
	private PostWriteActivity mContext;

	ImageAdapter(ArrayList<Uri> list, PostWriteActivity context) {
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

		//이미지 삭제
		holder.btnRecyclerviewImageDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				int positionToRemove = mData.indexOf(img_uri);
				if (positionToRemove != -1) {
					mData.remove(positionToRemove);
					notifyItemRemoved(positionToRemove);
					if (mData.size() < 3) {
						mContext.btnPostPicture.setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mData.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView image;
		ImageButton btnRecyclerviewImageDelete;
		Button btnPostPicture;

		ViewHolder(View itemView) {
			super(itemView);
			image = itemView.findViewById(R.id.ivRecyclerviewImage);
			btnRecyclerviewImageDelete = itemView.findViewById(R.id.btnRecyclerviewImageDelete);
			btnPostPicture = itemView.findViewById(R.id.btnPostPicture);
		}
	}
}
