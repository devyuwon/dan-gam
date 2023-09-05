package com.jica.dangam;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostProfileAdapter extends RecyclerView.Adapter<PostProfileAdapter.ViewHolder> {
	private ArrayList<PostProfile> items = new ArrayList<>();
	private Context context;

	public PostProfileAdapter(ArrayList<PostProfile> items) {
		this.items = items;
	}

	public PostProfileAdapter() {
	}

	;

	public PostProfileAdapter(Context context, ArrayList<PostProfile> list) {
		this.context = context;
		this.items = list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		PostProfile item = items.get(position);
		holder.setItemOnView(item);
	}

	@Override
	public int getItemCount() {
		int size = items != null ? items.size() : 0;
		Log.d("TAG", "PostProfileAdapter에서 현재 데이타의 갯수 :" + size);
		return size;
	}

	static public class ViewHolder extends RecyclerView.ViewHolder {
		private TextView title;
		private TextView content;
		private ImageView image;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			title = (TextView)itemView.findViewById(R.id.postTitle);
			content = (TextView)itemView.findViewById(R.id.postContents);
			image = (ImageView)itemView.findViewById(R.id.postImage);
		}

		public void setItemOnView(PostProfile item) {
			title.setText(item.getTitle());
			content.setText(item.getContents());
		}
	}

	public void addItem(PostProfile item) {
		items.add(item);
	}
	public ImageView getImageView(){
		return image;}
}
