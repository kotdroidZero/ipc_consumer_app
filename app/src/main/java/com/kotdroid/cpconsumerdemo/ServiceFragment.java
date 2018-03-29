package com.kotdroid.cpconsumerdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user12 on 29/3/18.
 */

public class ServiceFragment extends Fragment {

    @BindView(R.id.tvRandomNumber) TextView tvRandomNumber;

    private Intent serviceIntent;

    private LocalBoundService mLocalBoundService;

    private ServiceConnection mServiceConnection;
    private boolean isServiceBound = false;
    private int randomNumber;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        serviceIntent = new Intent(getContext(), LocalBoundService.class);

    }

    @OnClick({R.id.btnStartService, R.id.btnStopService, R.id.btnBindService, R.id.btnUnbindService, R.id.btnRandomNumber})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartService:
                //create an explicit intent
                serviceIntent.putExtra("msg", "The service has been started");
                getActivity().startService(serviceIntent);
                break;
            case R.id.btnStopService:
                getActivity().stopService(serviceIntent);
                break;
            case R.id.btnBindService:
                bindService();
                break;
            case R.id.btnUnbindService:
                unboundService();
                break;
            case R.id.btnRandomNumber:
                getRandomNumber();
                break;

        }
    }

    private void unboundService() {
        if (isServiceBound) {
            getActivity().unbindService(mServiceConnection);
            isServiceBound = false;
        }
        Log.d("ServiceDemo", "in unBoundService()");
    }

    private void bindService() {
        if (null == mServiceConnection) {
            mServiceConnection = new ServiceConnection() {
                @Override public void onServiceConnected(ComponentName name, IBinder service) {
                    LocalBoundService.MyServiceBinder binder = ((LocalBoundService.MyServiceBinder) service);
                    mLocalBoundService = binder.getService();
                    isServiceBound = true;
                }

                @Override public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }
        getActivity().bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.d("ServiceDemo", "in BindService()");
    }

    public void getRandomNumber() {
        if (isServiceBound) {
            tvRandomNumber.setText("The random number is: " + String.valueOf(mLocalBoundService.getRandomNumber()));
        } else
            tvRandomNumber.setText("Service is not bound");
    }
}
