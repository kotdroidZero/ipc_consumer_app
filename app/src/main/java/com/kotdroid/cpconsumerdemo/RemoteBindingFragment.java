package com.kotdroid.cpconsumerdemo;

import android.content.Intent;
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

public class RemoteBindingFragment extends Fragment {

    private Intent remoteIntent;

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
                remoteIntent = new Intent(getActivity(), RemoteBindingService.class);
                getActivity().startService(remoteIntent);
                break;
            case R.id.btnStopService:
                getActivity().stopService(remoteIntent);
                break;

        }
    }
}
