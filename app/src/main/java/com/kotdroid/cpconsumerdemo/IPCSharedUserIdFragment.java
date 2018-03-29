package com.kotdroid.cpconsumerdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user12 on 28/3/18.
 */

public class IPCSharedUserIdFragment extends Fragment {

    @BindView(R.id.btnPrefs) Button btnSave;
    @BindView(R.id.tvPrefsData) TextView tvTextMessage;
    @BindView(R.id.tvResources) TextView tvResources;
    @BindView(R.id.rootLayout) LinearLayout rootLayout;

    public static final String PKG_NAME_APPDEMO = "com.kotdroid.contentproviderdemo";
    private SharedPreferences sharedPreferences;
    private Context context;
    private Resources resources;

    @Nullable @Override public View onCreateView(LayoutInflater inflater,
                                                 @Nullable ViewGroup container,
                                                 @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ipc_shared_user_id, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        try {
            context = getContext().createPackageContext(PKG_NAME_APPDEMO,
                    Context.CONTEXT_IGNORE_SECURITY);
            resources = context.getResources();
            sharedPreferences = context.getSharedPreferences("IPCDemo", Context.MODE_PRIVATE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType") @OnClick({R.id.btnPrefs, R.id.btnStringResource, R.id.btnDrawableResource, R.id.btnColorResource})
    public void loadFile(View view) {
        switch (view.getId()) {
            case R.id.btnPrefs:
                tvTextMessage.setText(sharedPreferences.getString("testingIPC", null));
                break;
            case R.id.btnStringResource:
                int idString = resources.getIdentifier("stringconsumer", "string",
                        PKG_NAME_APPDEMO);
                tvResources.setText(resources.getString(idString));
                break;
            case R.id.btnDrawableResource:
                int idDrawable = resources.getIdentifier("test", "drawable",
                        PKG_NAME_APPDEMO);
                rootLayout.setBackground(resources.getDrawable(idDrawable));
                break;
            case R.id.btnColorResource:
                int idColor = resources.getIdentifier("colorDemo", "color", PKG_NAME_APPDEMO);
                rootLayout.setBackgroundColor(resources.getColor(idColor));
                break;
        }
    }

//    private void readFile(String fullPath) {
//        try {
//            FileInputStream fis = new FileInputStream(new File(fullPath));
//            int read = -1;
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((read = fis.read()) != -1) {
//                stringBuffer.append((char) read);
//            }
//            tvTextMessage.setText(stringBuffer.toString());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            tvTextMessage.setText(e.getLocalizedMessage());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            tvTextMessage.setText(e.getLocalizedMessage());
//        }
//    }

}
