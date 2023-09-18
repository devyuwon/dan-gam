package com.jica.dangam.list;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
	static private String kindString = "일감";

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
	public void addItems(ArrayList<ListModel> list){
		items.addAll(list);
	}

	public ListModel getItem(int index) {
		return this.items.get(index);
	}

	static public class ViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		TextView sal;
		ImageView image;
		TextView kind;
		TextView time;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			title = (TextView)itemView.findViewById(R.id.postTitle);
			sal = (TextView)itemView.findViewById(R.id.postSale);
			image = (ImageView)itemView.findViewById(R.id.postImage);
			kind = (TextView)itemView.findViewById(R.id.postKind);
			time = (TextView)itemView.findViewById(R.id.postTime);
			itemView.setClickable(true);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = getAdapterPosition();
					if (position != RecyclerView.NO_POSITION) {
						Intent intent = new Intent(context, PostItemActivity.class);
						PostModel post = new PostModel(items.get(position));
						intent.putExtra("post", post);
						context.startActivity(intent);
					}

				}
			});
		}

		public void setItemOnView(ListModel item) {
			title.setText(item.getTitle());
			sal.setText(item.getReward());


			String imageUrl = item.getImageUrl1(); // 이미지 URL 가져오기
			if (imageUrl != null && !imageUrl.isEmpty()) {
				FirebaseStorage storage = FirebaseStorage.getInstance();
				StorageReference gsReference = storage.getReferenceFromUrl(imageUrl);
				Glide.with(itemView).load(imageUrl).into(image);
			} else {
				Glide.with(itemView).load(R.drawable.img_symbol02).into(image);
			}
			if(kindString == "post_gam"){
				kind.setText("일감");
			}else {
				kind.setText("일꾼");
			}

		}
	}

	public void clearData() {
		items.clear();
	}
	public void setKind(String kind){
		this.kindString = kind;
	}
}