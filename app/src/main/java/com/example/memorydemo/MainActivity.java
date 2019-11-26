package com.example.memorydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 内存泄漏问题研究
 * https://www.jianshu.com/p/65f914e6a2f8
 *
 * @author zhoguang@unipus.cn
 * @date 2019/11/22 13:36
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_add;
    private Button bt_query;
    private EditText et_name, et_age, et_query_requirement;
    private TextView tv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_add = findViewById(R.id.bt_add);
        bt_query = findViewById(R.id.bt_query);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        tv_list = findViewById(R.id.tv_list);
        et_query_requirement = findViewById(R.id.et_query_requirement);

        bt_add.setOnClickListener(this);
        bt_query.setOnClickListener(this);

        List<InfoBean> list2 = DatabaseHelper.getAll(this);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (InfoBean info : list2) {
            stringBuffer2.append(info.getName() + ":" + info.getAge() + "\n");
        }
        tv_list.setText(stringBuffer2.toString());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                DatabaseHelper.insertInfo(this, new InfoBean(et_name.getText().toString(), Integer.valueOf(et_age.getText().toString())));
                List<InfoBean> list2 = DatabaseHelper.getAll(this);
                StringBuffer stringBuffer2 = new StringBuffer();
                for (InfoBean info : list2) {
                    stringBuffer2.append(info.getName() + ":" + info.getAge() + "\n");
                }
                tv_list.setText(stringBuffer2.toString());

                break;
            case R.id.bt_query:
                if (TextUtils.isEmpty(et_query_requirement.getText().toString())) {
                    List<InfoBean> list = DatabaseHelper.getAll(this);
                    StringBuffer stringBuffer = new StringBuffer();
                    for (InfoBean info : list) {
                        stringBuffer.append(info.getName() + ":" + info.getAge() + "\n");
                    }

                    Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        InfoBean info = DatabaseHelper.queryById(this, Integer.valueOf(et_query_requirement.getText().toString()));
                        Toast.makeText(this, info.getName() + ":" + info.getAge(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "请输入数字id", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
        }
    }
}
