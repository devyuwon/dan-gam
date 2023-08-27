package com.jica.dangam;

import java.util.ArrayList;

import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	PostProfileAdapter adapter;
	LinearLayoutManager linearLayoutManager;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar tb = (Toolbar) findViewById(R.id.mainToolbar) ;
		setSupportActionBar(tb) ;

		recyclerView = findViewById(R.id.main_recyclerview);

		linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
		recyclerView.setLayoutManager(linearLayoutManager);

		adapter = new PostProfileAdapter();


		//test용 더미데이터

		adapter.addItem(new PostProfile("제목1","내용1"));
		adapter.addItem(new PostProfile("제목2","내용2"));
		adapter.addItem(new PostProfile("제목3","내용3"));
		adapter.addItem(new PostProfile("제목4","내용4"));
		adapter.addItem(new PostProfile("제목5","내용5"));
		adapter.addItem(new PostProfile("제목6","내용6"));
		adapter.addItem(new PostProfile("제목7","내용7"));
		adapter.addItem(new PostProfile("제목8","내용8"));

		recyclerView.setAdapter(adapter);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_toolbar, menu) ;
		return super.onCreateOptionsMenu(menu);
	}
}
