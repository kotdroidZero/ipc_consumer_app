package com.kotdroid.cpconsumerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user12 on 29/3/18.
 */

public class IPCServiceFragment extends Fragment {

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ipc_using_service, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.btnStartService, R.id.btnStopService})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartService:
                break;
            case R.id.btnStopService:
                break;

        }
    }
}
