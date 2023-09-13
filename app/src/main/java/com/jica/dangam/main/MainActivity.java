package com.jica.dangam.main;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jica.dangam.PostWriteActivity;
import com.jica.dangam.mypage.MyPageFragment;
import com.jica.dangam.R;
import com.jica.dangam.login.LoginActivity;
import com.jica.dangam.mypage.MyPageFragment;
import com.jica.dangam.search.SearchFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import es.dmoral.toasty.Toasty;

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

		Toasty.Config.getInstance()
			.tintIcon(true)
			.setTextSize(14)
			.allowQueue(true)
			.apply();
	}

	public void logoutToLoginActivity(Boolean result) {
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);

		if (result) {
			Toasty.success(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG, true).show();
		} else {
			Toasty.warning(getApplicationContext(), "로그아웃 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT, true).show();
		}
		intent.addFlags(
			Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	public void deleteToLoginActivity(Boolean result) {
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);

		if (result) {
			Toasty.success(getApplicationContext(), "회원 탈퇴가 정상 처리되었습니다. 나중에 다시 찾아주세요!", Toast.LENGTH_LONG, true).show();
		} else {
			Toasty.warning(getApplicationContext(), "회원 탈퇴 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT, true).show();
		}
		intent.addFlags(
			Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
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
