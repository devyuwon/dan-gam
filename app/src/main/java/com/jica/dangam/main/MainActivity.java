package com.jica.dangam.main;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jica.dangam.PostWriteActivity;
import com.jica.dangam.mypage.MyPageFragment;
import com.jica.dangam.R;
import com.jica.dangam.search.SearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
	private FragmentManager fragmentManager = getSupportFragmentManager();
	private MainFragment mainFragment = new MainFragment();
	private SearchFragment searchFragment = new SearchFragment();
	private MyPageFragment myPageFragment = new MyPageFragment();

	@Override
	protected void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_main);

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.menuFrameLayout, mainFragment).commitAllowingStateLoss();

		BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
		bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListner());
	}

	class ItemSelectedListner implements BottomNavigationView.OnNavigationItemSelectedListener {
		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			FragmentTransaction transaction = fragmentManager.beginTransaction();

			int itemId = item.getItemId();
			if (itemId == R.id.menu_home) {
				transaction.replace(R.id.menuFrameLayout, mainFragment).commitAllowingStateLoss();
			} else if (itemId == R.id.menu_search) {
				transaction.replace(R.id.menuFrameLayout, searchFragment).commitAllowingStateLoss();
			} else if (itemId == R.id.menu_my_page) {
				transaction.replace(R.id.menuFrameLayout, myPageFragment).commitAllowingStateLoss();
			} else if (itemId == R.id.menu_write){
				Intent intent = new Intent(getApplicationContext(), PostWriteActivity.class);
				startActivity(intent);
			}
			return true;
		}
	}
}
