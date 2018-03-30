package com.kotdroid.cpconsumerdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etAge) EditText etAge;
    @BindView(R.id.etName) EditText etName;

    public static final Uri USER_URI = Uri.parse("content://com.kotdroid.contentproviderdemo.usercontentprovider/demoCPTable");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        findViewById(R.id.flContainerMain).setVisibility(View.GONE);

        Log.d("ServiceDemo", "MainActivity Thread id:" + Thread.currentThread().getId());
    }

    @Override public void onBackPressed() {
        if (0 < getSupportFragmentManager().getBackStackEntryCount()) {
            getSupportFragmentManager().popBackStackImmediate();
            findViewById(R.id.flContainerMain).setVisibility(View.GONE);
        } else super.onBackPressed();
    }

    @OnClick({R.id.btnSubmit, R.id.btnOpenFragment, R.id.btnStartService, R.id.btnIPCRemoteBinding})
    public void onSubmit(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:
                String intentCP = "com.kotdroid.contentproviderdemo.ContactActivity";
                ContentResolver contentResolver = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", etName.getText().toString().trim());
                contentValues.put("age", etAge.getText().toString().trim());
                contentValues.put("destination", intentCP);
        /*Uri uri = */
                contentResolver.insert(USER_URI, contentValues);
       /* assert uri != null;
        Toast.makeText(this, "data inserted with row id:" + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();*/
                break;
            case R.id.btnOpenFragment:
                findViewById(R.id.flContainerMain).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain, new IPCSharedUserIdFragment()).addToBackStack(null).commit();
                break;
            case R.id.btnStartService:
                findViewById(R.id.flContainerMain).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain, new LocalBindingFragment()).addToBackStack(null).commit();
                break;
            case R.id.btnIPCRemoteBinding:
                findViewById(R.id.flContainerMain).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().add(R.id.flContainerMain, new RemoteBindingFragment()).addToBackStack(null).commit();
                break;

        }
    }
}
