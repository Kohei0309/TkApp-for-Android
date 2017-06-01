package tk.com.tkapplication;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tk.com.tkapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainViewModel viewModel = new MainViewModel();
        binding.setViewModel(viewModel);
    }

    public class MainViewModel extends BaseObservable {

        public void onClickButtonSalonApp(View view) {
            startActivity(HybridActivity.createIntent(getBaseContext(), "https://adspecial008.wixsite.com/salon"));
        }

        public void onClickButtonEcApp(View view) {
            startActivity(HybridActivity.createIntent(getBaseContext(), "https://adspecial008.wixsite.com/mysite-2"));
        }
    }
}
