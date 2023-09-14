package com.jica.dangam.list;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jica.dangam.R;
import com.jica.dangam.post.PostItemActivity;
import com.jica.dangam.post.PostModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

	static private ArrayList<ListModel> items;
	static private Context context;


	public ListAdapter(Context context, ArrayList<ListModel> list) {
		this.context = context;
		this.items = list;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.list_contents, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		context = recyclerView.getContext();
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		ListModel item = items.get(position);
		holder.setItemOnView(item);
	}

	@Override
	public int getItemCount() {
		int size = items != null ? items.size() : 0;
		Log.d("TAG", "PostProfileAdapter에서 현재 데이타의 갯수 :" + size);
		return size;
	}

	public void addItem(ListModel item) {
		items.add(item);
	}

	public ListModel getItem(int index) {
		return this.items.get(index);
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
			itemView.setClickable(true);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = getAdapterPosition();
					if(position != RecyclerView.NO_POSITION){
						Intent intent = new Intent(context, PostItemActivity.class);
						PostModel post = new PostModel(items.get(position));
						intent.putExtra("post",post);
						context.startActivity(intent);
					}

				}
			});
		}

		public void setItemOnView(ListModel item) {
			title.setText(item.getTitle());
			content.setText(item.getContents());
			if (item.getImageUrl1() != "") {
				FirebaseStorage storage = FirebaseStorage.getInstance();
				StorageReference gsReference = storage.getReferenceFromUrl(item.getImageUrl1());
				Glide.with(itemView).load(item.getImageUrl1()).into(image);

				Log.d("TAG", "Glide 작동");
			}
		}
	}
	public void clearData(){
		items.clear();
	}
}
