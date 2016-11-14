package com.example.verunex.helpmate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LoginSubPage extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_sub_page, container, false);
        Button bt = (Button) rootView.findViewById(R.id.buttonZaloguj);
        bt.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonZaloguj:
                test();
                break;
        }
    }

    public void test(){
        Toast.makeText(getContext(), "asdassssssssd",Toast.LENGTH_SHORT).show();
    }


}
