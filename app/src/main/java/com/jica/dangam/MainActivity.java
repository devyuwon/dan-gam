package com.jica.dangam;

import java.util.ArrayList;

import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar tb = (Toolbar) findViewById(R.id.mainToolbar) ;
		setSupportActionBar(tb) ;





	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_toolbar, menu) ;
		return super.onCreateOptionsMenu(menu);
	}
}
