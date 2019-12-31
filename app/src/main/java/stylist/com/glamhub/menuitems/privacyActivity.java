package stylist.com.glamhub.menuitems;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import stylist.com.glamhub.R;


public class privacyActivity extends AppCompatActivity {
    public WebView mWebView;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        mWebView = (WebView) findViewById(R.id.wvprivacy);



        mWebView.loadUrl("http://joslabs.co.ke/mapsprivacy/");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new WebViewClient()

                                  {
                                      @Override
                                      public void onPageFinished(WebView view, String url) {
                                          super.onPageFinished(view, url);
               /* if(progressDialog.isShowing())
                progressDialog.dismiss();*/
                                      }
                                      public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                          return true;
                                      }
                                  }
        );
    }

}
