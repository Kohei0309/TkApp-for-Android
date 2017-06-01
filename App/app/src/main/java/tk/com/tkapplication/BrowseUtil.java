package tk.com.tkapplication;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;

/**
 * Created by matsushita-pc on 2017/06/01.
 */

public class BrowseUtil {

    /**
     * ブラウザを表示
     *
     * @param activity Activity
     * @param url URL
     */
    public static void launch(@NonNull Activity activity, String url) {
        if (!TextUtils.isEmpty(url)) {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

            // CustomTabsでURLをひらくIntentを発行
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(activity, Uri.parse(url));
        }
    }

    /**
     * ホワイトリストかどうかURLをチェックする
     *
     * @param url URL
     * @return ホワイトリストかどうか
     */
    public static boolean checkUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            // PDFファイルは外部ブラウザに委譲
            if (url.endsWith(".pdf")) {
                return true;
            }
            // このドメインはWebViewで許容
            if (TextUtils.equals("www.ilex.ac", uri.getHost())) {
                return false;
            }
        }
        return true;
    }
}
