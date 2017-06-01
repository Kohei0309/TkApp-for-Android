package tk.com.tkapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tk.com.tkapplication.databinding.ActivityHybridBinding;

public class HybridActivity extends AppCompatActivity {

    private static final String TAG = HybridActivity.class.getSimpleName();

    private static final String INTENT_URL = "INTENT_URL";

    private ActivityHybridBinding binding;

    private HybridViewModel viewModel;

    public static Intent createIntent(Context context, String url) {
        Intent intent = new Intent(context, HybridActivity.class);
        intent.putExtra(INTENT_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hybrid);

        viewModel = new HybridViewModel();
        binding.setViewModel(viewModel);

        // WebViewを初期化
        String url = getIntent().getStringExtra(INTENT_URL);
        viewModel.initWebView(new MyWebViewClient(), new MyWebChromeClient(), url);
    }

    @Override
    public void onBackPressed() {
        // WebViewのスタックがないときは通常のBack操作を行う
        if (viewModel.canGoBack()) {
            viewModel.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ブラウザを起動
     *
     * @param url URL
     */
    private void launchBrowse(String url) {
        BrowseUtil.launch(this, url);
    }

    /**
     * WebViewClient 継承クラス
     */
    private class MyWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "URLを判定します : " + url);
            // テスト版なのでチェックしない
//            if (BrowseUtil.checkUrl(url)) {
//                launchBrowse(url);
//                return true;
//            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "読み込み開始");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG, "読み込み終了");
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String url) {

        }
    }

    /**
     * WebChromeClient 継承クラス
     */
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            viewModel.onProgressChanged(newProgress);
        }
    }

    public class HybridViewModel extends BaseObservable {

        /**
         * WebView を初期化
         *
         * @param webViewClient WebViewClient
         * @param webChromeClient WebChromeClient
         * @param url URL
         */
        @SuppressLint("SetJavaScriptEnabled")
        public void initWebView(WebViewClient webViewClient, WebChromeClient webChromeClient, String url) {
            binding.webView.getSettings().setJavaScriptEnabled(true);

            binding.webView.setWebViewClient(webViewClient);
            binding.webView.setWebChromeClient(webChromeClient);
            binding.webView.loadUrl(url);
        }

        /**
         * WebView のスタックがあるかどうか
         *
         * @return true:スタックがある false:スタックがない
         */
        public boolean canGoBack() {
            return binding.webView.canGoBack();
        }

        /**
         * WebView を一つ戻る
         */
        public void goBack() {
            binding.webView.goBack();
        }

        /**
         * プログレスバーのステータスを変更
         *
         * @param newProgress 読み込みステータス（0~100）
         */
        public void onProgressChanged(int newProgress) {
            binding.progressBar.setProgress(newProgress);
        }
    }
}
