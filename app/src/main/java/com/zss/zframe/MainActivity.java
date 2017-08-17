package com.zss.zframe;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zss.library.PermissionCallBack;
import com.zss.library.activity.BaseActivity;
import com.zss.library.activity.BrowserActivity;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.recyclerview.CommonAdapter;
import com.zss.library.adapter.recyclerview.ViewHolder;
import com.zss.library.activity.PhotoPickerActivity;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.utils.CommonToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 17/6/6.
 */
public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CommonAdapter<String[]>(R.layout.item_main, getDatas()){
            @Override
            protected void convert(ViewHolder viewHolder, String[] item, int position) {
                TextView title = viewHolder.findViewById(R.id.title);
                TextView summary = viewHolder.findViewById(R.id.summary);
                title.setText(item[0]);
                summary.setText(item[1]);
            }

            @Override
            protected void onItemClick(View view, String[] item, int position) {
                String mStr = item[0];
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                if(mStr.equals("TabLayout")){
                    intent.putExtra(ZFrameActivity.CLASS, TestTabLayoutFragment.class);
                } else if(mStr.equals("BaseFragment")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestBaseFragment.class);
                } else if(mStr.equals("CommonAppBar")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestAppBarFragment.class);
                } else if(mStr.equals("CommonText")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestTextWidgetFragment.class);
                } else if(mStr.equals("CommonEdit")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestEditWidgetFragment.class);
                } else if(mStr.equals("CommonSelect")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestSelectWidgetFragment.class);
                } else if(mStr.equals("CommonSwitch")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestSwitchWidgetFragment.class);
                } else if(mStr.equals("CommonDialog")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestDialogFragment.class);
                } else if(mStr.equals("CommonWheelDialog")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestWheelDialogFragment.class);
                } else if(mStr.equals("CommonProgressDialog")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestProgressDialogFragment.class);
                } else if(mStr.equals("ListView/CommonAdapter")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestListAdapterFragment.class);
                } else if(mStr.equals("RecyclerView/CommonAdapter")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestRecyclerAdapterFragment.class);
                } else if(mStr.equals("ptrm")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestPtrmFragment.class);
                } else if(mStr.equals("AdViewPager")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestAdViewPagerFragment.class);
                } else if(mStr.equals("PhotoPicker")) {
                    openPhotoPicker();
                    return;
                } else if(mStr.equals("BrowserPager")) {
                    openBrowser();
                    return;
                } else if(mStr.equals("https")) {
                    CommonToastUtils.showInCenterToast(getActivity(), "没有编写例子，com.zss.library.https.OkHttpUtils");
                    return;
                } else if(mStr.equals("JsonRpc")) {
                    intent.putExtra(ZFrameActivity.CLASS, TestJsonRpcFragment.class);
                }
                startActivity(intent);
            }
        });
    }

    public void openBrowser(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(R.mipmap.bb1);
        list.add(R.mipmap.bb2);
        list.add(R.mipmap.bb3);
        list.add(R.mipmap.bb4);
        Intent intent = new Intent(getActivity(), BrowserActivity.class);
        intent.putExtra(BrowserActivity.EXTRA_POSITION, 1);
        intent.putExtra(BrowserActivity.EXTRA_LOAD_TYPE, BrowserActivity.RES);
        //EXTRA_LOAD_TYPE设置RES，intent需要设置putIntegerArrayListExtra(EXTRA_DATAS, xxx);
        //EXTRA_LOAD_TYPE设置URL、FILE，intent需要设置putStringArrayListExtra(EXTRA_DATAS, xxx);
        intent.putIntegerArrayListExtra(BrowserActivity.EXTRA_DATAS, list);
        startActivity(intent);
    }

    public void openPhotoPicker(){
        //申请权限
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        //打开相册和相机
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
                        startActivityForResult(intent, 0x02);
                    }

                    @Override
                    public void onDenied() {
                    }
                });
    }

    public List<String []> getDatas(){
        List<String []> datas = new ArrayList<>();
        String [] titles = new String[]{
                "TabLayout",
                "BaseFragment",
                "CommonAppBar",
                "CommonText",
                "CommonEdit",
                "CommonSelect",
                "CommonSwitch",
                "CommonDialog",
                "CommonWheelDialog",
                "CommonProgressDialog",
                "ListView/CommonAdapter",
                "RecyclerView/CommonAdapter",
                "ptrm",
                "AdViewPager",
                "BrowserPager",
                "PhotoPicker",
                "https",
                "JsonRpc"
        };
        String [] summarys = new String[]{
                "TabLayout首页效果",
                "用Fragment实现activity堆栈效果",
                "整合ToolBar并且支持标题向中心对齐和右边一个定制按钮",
                "TextView公用组件",
                "EditText公用组件",
                "公用选择组件，包括单选和多选",
                "公用开关组件",
                "公用对话框",
                "仿IPhone滚轮对话框",
                "公用锁屏对话框",
                "ListView公用适配器",
                "RecyclerView公用适配器",
                "支持下拉刷新和上拉加载更多",
                "广告轮播，无限轮播",
                "预览图片（支持左右滑动放大缩小)",
                "仿微信拍照和文件预览",
                "支持https访问和Glide加载图片https访问",
                "JsonRpc远程调用"
        };
        for(int i=0; i<titles.length; i++){
            datas.add(new String[]{titles[i], summarys[i]});
        }
        return datas;
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
    }

    @Override
    public void onBackPressed() {
        CommonToastUtils.exitClient(getActivity(), true);
    }
}
