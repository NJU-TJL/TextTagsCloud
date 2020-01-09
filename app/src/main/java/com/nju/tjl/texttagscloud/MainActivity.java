package com.nju.tjl.texttagscloud;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagCloudView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TagCloudView myCloud;
    EditText editText;
    TextTagsAdapter textTagsAdapter;
    TextView nameText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTagsAdapter=new TextTagsAdapter(this.getFilesDir().getAbsolutePath()+ File.separator);
        myCloud=(TagCloudView)findViewById(R.id.tag_cloud);
        myCloud.setAdapter(textTagsAdapter);
        editText=(EditText) findViewById(R.id.editText);
        nameText=(TextView) findViewById(R.id.nameText);
    }

    public void btnAdd_clicked(View view){
        String str=editText.getText().toString();
        if(!str.equals("")){
            textTagsAdapter.addTag(str);
        }
    }

    public void randomShow(View view){
        nameText.setText(textTagsAdapter.ramdonGet());
    }
    public void btnClear_clicked(View view){
        AlertDialog dialog = new AlertDialog.Builder(this)
//                        .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("清空所有")//设置对话框的标题
                .setMessage(R.string.clear_dialog)//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textTagsAdapter.clear();
                    }
                }).create();
        dialog.show();
    }

}
