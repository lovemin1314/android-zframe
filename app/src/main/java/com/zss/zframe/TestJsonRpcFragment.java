package com.zss.zframe;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.zss.library.appbar.CommonAppBar;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonTextWidget;
import com.zss.library.widget.CommonWheelDialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.attr.path;

/**
 * Created by zm on 17/8/17.
 */
public class TestJsonRpcFragment extends BaseFragment{

    private CommonTextWidget widget1;
    private TextView message;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_json_rpc;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (CommonTextWidget)findViewById(R.id.widget1);
        message = (TextView) findViewById(R.id.message);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        widget1.setLeftText("JsonRpc调用");
        widget1.setRightImageResource(R.mipmap.right_arrow);
        widget1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRpc();
            }
        });
    }

    public void sendRpc(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://172.16.0.29:8081/rpc";
                    JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(path));
                    String[] msg = new String[] { "zhangmiao" };
                    final String response = client.invoke("getString", msg, String.class);
                    message.post(new Runnable() {
                        @Override
                        public void run() {
                            message.setText(response);
                        }
                    });
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonAppBar toolbar = getAppBar();
        toolbar.setTitle("CommonWheelDialog");
    }
}
