package com.example.emacasaet.zendesktest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import io.fabric.sdk.android.Fabric;

import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.support.SupportActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this);
        setContentView(R.layout.activity_main);

        Logger.setLoggable(BuildConfig.DEBUG);
        initializeZendesk();

        findViewById(R.id.launch_help_center_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start SupportActivity to browse Help Center and create/update requests
                new SupportActivity.Builder().show(MainActivity.this);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeZendesk() {
        // Initialize the Support SDK with your Zendesk Support subdomain, mobile SDK app ID, and client ID.
        // Get these details from your Zendesk Support dashboard: Admin -> Channels -> Mobile SDK
        ZendeskConfig.INSTANCE.init(getApplicationContext(),
                getString(R.string.com_zendesk_sdk_url),
                getString(R.string.com_zendesk_sdk_identifier),
                getString(R.string.com_zendesk_sdk_clientIdentifier));


        // Authenticate anonymously as a Zendesk Support user
        ZendeskConfig.INSTANCE.setIdentity(
                new AnonymousIdentity.Builder()
                        .withNameIdentifier("John Bob")
                        .withEmailIdentifier("johnbob@example.com")
                        .build()
        );
    }

}
