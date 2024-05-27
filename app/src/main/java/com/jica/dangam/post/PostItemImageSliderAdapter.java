package com.jica.dangam.post;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.jica.dangam.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostItemImageSliderAdapter extends RecyclerView.Adapter<PostItemImageSliderAdapter.MyViewHolder> {
	private Context context;

	private ArrayList<String> sliderImage = new ArrayList<>();
	// private String[] sliderImage;

	public PostItemImageSliderAdapter(Context context) {
		this.context = context;
	}

	public PostItemImageSliderAdapter(Context context, ArrayList<String> sliderImage) {
		this.context = context;
		this.sliderImage = sliderImage;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
			.inflate(R.layout.post_image_slider, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		holder.bindSliderImage(sliderImage.get(position));
	}

	@Override
	public int getItemCount() {
		return sliderImage.size();
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		private ImageView mImageView;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageView = itemView.findViewById(R.id.imageSlider);
		}

		public void bindSliderImage(String imageURL) {
			Glide.with(context)
				.load(imageURL)
				.into(mImageView);
		}
	}

	public void addItem(String url) {
		sliderImage.add(url);
	}
}
