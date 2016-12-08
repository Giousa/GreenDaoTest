package com.giou.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.giou.greendaotest.gen.UserBeanDao;
import com.giou.greendaotest.manager.GreenDaoManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.btn_create)
    Button mBtnCreate;
    @InjectView(R.id.btn_query)
    Button mBtnQuery;
    @InjectView(R.id.btn_update)
    Button mBtnUpdate;
    @InjectView(R.id.btn_delete)
    Button mBtnDelete;
    @InjectView(R.id.btn_query_all)
    Button mBtnQueryAll;
    private UserBeanDao mUserBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mUserBeanDao = GreenDaoManager.getInstance().getUserBeanDao();

    }

    @OnClick({R.id.btn_create, R.id.btn_query, R.id.btn_update, R.id.btn_delete,R.id.btn_query_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                insertData();
                break;
            case R.id.btn_query:
                queryData();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_delete:
                deleteData();
                break;

            case R.id.btn_query_all:
                queryAllData();
                break;
        }
    }

    private int count = 0;

    private void insertData() {
        UserBean giou = new UserBean(null, "Giou" + count, true, 27 + count);
        count++;
        long insert = mUserBeanDao.insert(giou);
        Log.d(TAG, "添加数据成功！！" + insert);
    }

    private void queryData() {
        List<UserBean> userBeanList = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Name.in("Giou5"))
                .orderAsc(UserBeanDao.Properties.Id).limit(5).build().list();
        if (userBeanList != null && userBeanList.size() > 0) {
            for (int i = 0; i < userBeanList.size(); i++) {
                Log.d(TAG, "userBeanList:" + userBeanList.get(i).toString());
            }
        }
    }

    private void updateData() {
        UserBean findUser = mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq("Giou5")).build().unique();
        if (findUser != null) {
            findUser.setName("博丽灵梦");
            mUserBeanDao.update(findUser);
            Log.d(TAG, "修改成功");
        } else {
            Log.d(TAG, "修改失败");
        }
    }

    private void deleteData() {
        UserBean findUser = mUserBeanDao.queryBuilder().where(UserBeanDao.Properties.Name.eq("Giou6")).build().unique();
        if (findUser != null) {
            mUserBeanDao.deleteByKey(findUser.getId());
            Log.d(TAG, "删除成功");
        }else{
            Log.d(TAG, "删除成功");
        }
    }

    private void queryAllData() {
        List<UserBean> userBeanList = mUserBeanDao.queryBuilder()
                .where(UserBeanDao.Properties.Id.notEq(999))
                .orderAsc(UserBeanDao.Properties.Id).limit(100).build().list();
        if (userBeanList != null && userBeanList.size() > 0) {
            for (int i = 0; i < userBeanList.size(); i++) {
                Log.d(TAG, "userBeanList:" + userBeanList.get(i).toString());
            }
        }
    }
}
