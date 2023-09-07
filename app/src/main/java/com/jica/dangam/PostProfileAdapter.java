package com.jica.dangam;

import java.util.ArrayList;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import android.util.Log;
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
	StorageReference storageReference = FirebaseStorage.getInstance().getReference();

	;

	public PostProfileAdapter(Context context, ArrayList<PostProfile> list) {
		this.context = context;
		this.items = list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.post_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		context = recyclerView.getContext();
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
			if(item.getImage1() != null){
				Glide.with(itemView).load(item.getImage1()).into(image);
				Log.d("TAG","Glide 작동");
			}
		}
	}

	public void addItem(PostProfile item) {
		items.add(item);
	}
	public PostProfile getItem(int index){
		return this.items.get(index);
	}

}
