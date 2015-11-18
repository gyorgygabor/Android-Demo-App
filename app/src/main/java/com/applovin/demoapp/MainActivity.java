package com.applovin.demoapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applovin.demoapp.rewarded.RewardedVideosActivity;
import com.applovin.sdk.AppLovinSdk;

/**
 * Created by thomasso on 10/5/15.
 */
public class MainActivity extends BaseActivity {
    private static final int POSITION_INTERSTITIALS = 0;
    private static final int POSITION_INCENTIVIZED  = 1;
    private static final int POSITION_NATIVE        = 2;
    private static final int POSITION_RESOURCES     = 3;
    private static final int POSITION_CONTACT       = 4;

    private ListView listView;
    private ListItem[] items = {
            new ListItem("Interstitials", "Full screen ads. Graphic or video"),
            new ListItem("Rewarded Videos (Incentivized Ads)", "Reward your users for watching these on-demand videos"),
            new ListItem("Native Ads", "In-content ads that blend in seamlessly"),
            new ListItem("Resources", "https://support.applovin.com/support/home"),
            new ListItem("Contact", "support@applovin.com")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Initializing our SDK at launch is very important as it'll start preloading ads in the background.
        //
        AppLovinSdk.initializeSdk(getApplicationContext());

        // Initialize list view
        listView = (ListView) findViewById(R.id.listView);
        setupListViewFooter();

        // Behind the scenes, the array adapter takes each item in the array,
        // converts it to a String using its <BOLD>toString()</BOLD> method and puts each result into a text view.
        // It then displays each text view as as single row in teh list view
        ArrayAdapter<ListItem> listAdapter = new ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_2, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View row = convertView;
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                ListItem item = items[position];

                TextView title = (TextView) row.findViewById(android.R.id.text1);
                title.setText(item.getTitle());
                TextView subtitle = (TextView) row.findViewById(android.R.id.text2);
                subtitle.setText(item.getSubtitle());

                return row;
            }
        };
        listView.setAdapter(listAdapter);


        // If ListActivity -> @Override public void onListItemClick( . . . .
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == POSITION_INTERSTITIALS) {
                    Intent intent = new Intent(MainActivity.this, InterstitialListViewActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_INCENTIVIZED) {
                    Intent intent = new Intent(MainActivity.this, RewardedVideosActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_NATIVE) {
                    Intent intent = new Intent(MainActivity.this, NativeAdListViewActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_RESOURCES) {
                    openSupportWebpage();
                } else if (position == POSITION_CONTACT) {
                    openContactSupport();
                }
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }

    public void setupListViewFooter() {
        String appVersion = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView footer = new TextView(getApplicationContext());
        footer.setPadding(0, 20, 0, 0);
        footer.setGravity(Gravity.CENTER);
        footer.setTextSize(18);
        footer.setText("\nApp Version: " + appVersion +
                "\nSDK Version: " + AppLovinSdk.VERSION +
                "\n\nSDK Key: " + AppLovinSdk.getInstance(getApplicationContext()).getSdkKey());

        listView.addFooterView(footer);
        listView.setFooterDividersEnabled(false);
    }

    public void openSupportWebpage() {
        final String url = "https://support.applovin.com/support/home";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void openContactSupport() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:" + "support@applovin.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Android SDK support");
        intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n---\nSDK Version: " + AppLovinSdk.VERSION);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private class ListItem {
        private final String title;
        private final String subtitle;

        ListItem(final String title, final String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }
    }
}
