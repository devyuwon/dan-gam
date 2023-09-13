package com.jica.dangam.post;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.jica.dangam.R;

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

public class PostImageAdapter extends RecyclerView.Adapter<PostImageAdapter.ViewHolder> {
	private ArrayList<Uri> mData = null;
	private PostCreateActivity mContext;

	PostImageAdapter(ArrayList<Uri> list, PostCreateActivity context) {
		mData = list;
		mContext = context;
	}

	@NonNull
	@Override
	public PostImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.recyclerview_post_write_image, parent, false);
		PostImageAdapter.ViewHolder vh = new PostImageAdapter.ViewHolder(view);

		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull PostImageAdapter.ViewHolder holder, int position) {
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
