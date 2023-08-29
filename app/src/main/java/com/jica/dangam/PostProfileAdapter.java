package com.jica.dangam;
import java.util.ArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostProfileAdapter extends RecyclerView.Adapter<PostProfileAdapter.ViewHolder>{
	ArrayList<PostProfile> items = new ArrayList<>();
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		PostProfile item = items.get(position);
		holder.setItem(item);
	}

	@Override
	public int getItemCount() {
		return (null != items ? items.size() : 0);
	}

	static public class ViewHolder extends RecyclerView.ViewHolder{
		public TextView title;
		public TextView content;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.postTitle);
			content = (TextView) itemView.findViewById(R.id.postContents);
		}

		public void setItem(PostProfile item) {
			title.setText(item.getTitle());
			content.setText(item.getContents());
		}
	}

	public void addItem(PostProfile item) {
		items.add(item);
	}
	public void addAll(ArrayList<PostProfile> list){
		items.addAll(list);
	}
}
