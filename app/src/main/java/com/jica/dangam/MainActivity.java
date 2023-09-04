package com.jica.dangam;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
	// private MyPageFragment myPageFragment;
	// private Button btnMyPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// myPageFragment = new MyPageFragment();
		//
		// FragmentManager fragmentManager = getSupportFragmentManager();
		// FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		//
		// fragmentTransaction.add(R.id.frameLayout, myPageFragment);
		// fragmentTransaction.commit();
		//
		// btnMyPage = findViewById(R.id.btnMyPage);
		// btnMyPage.setOnClickListener(view -> {
		// 	FragmentManager manager = getSupportFragmentManager();
		// 	FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 	transaction.replace(R.id.frameLayout, myPageFragment);
		// 	transaction.commit();
		// });
	}
}
