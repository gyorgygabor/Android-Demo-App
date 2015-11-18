package com.applovin.demoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.applovin.demoapp.interstitials.InterstitialManualLoadingActivity;
import com.applovin.demoapp.interstitials.InterstitialSharedInstanceActivity;
import com.applovin.demoapp.interstitials.InterstitialSingleInstanceActivity;

public class InterstitialListViewActivity extends BaseActivity {

    private static final int POSITION_INSTANCE = 0;
    private static final int POSITION_SHARED   = 1;
    private static final int POSITION_MANUAL   = 2;

    private ListItem[] items = {
            new ListItem("Single instance", "Programatically creating an instance of it"),
            new ListItem("Shared instance", "Use the shared instance"),
            new ListItem("Manually loading ad", "Use this for greater control over the ad load process")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_list_view);

        ListView listView = (ListView) findViewById(R.id.listView);
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

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == POSITION_INSTANCE) {
                    Intent intent = new Intent(InterstitialListViewActivity.this, InterstitialSingleInstanceActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_SHARED) {
                    Intent intent = new Intent(InterstitialListViewActivity.this, InterstitialSharedInstanceActivity.class);
                    startActivity(intent);
                } else if (position == POSITION_MANUAL) {
                    Intent intent = new Intent(InterstitialListViewActivity.this, InterstitialManualLoadingActivity.class);
                    startActivity(intent);
                }
            }
        };
        listView.setOnItemClickListener(itemClickListener);
    }
}
