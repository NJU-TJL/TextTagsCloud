package com.nju.tjl.texttagscloud;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Created by 唐Jinlin on 2020/1/8.
 */

public class TextTagsAdapter extends TagsAdapter {
    String filename;
    private List<String> dataSet = new ArrayList<>();
    public TextTagsAdapter(String filedir){
        filename=filedir+"myTags.txt";
        readFile();
    }


    @Override
    public int getCount() {
        return dataSet.size();
    }


    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(dataSet.get(position));
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
//                        .setIcon(R.mipmap.icon)//设置标题的图片
                        .setTitle("删除")//设置对话框的标题
                        .setMessage(R.string.delete_dialog)//设置对话框的内容
                        //设置对话框的按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTag(position);
                            }
                        }).create();
                dialog.show();
            }
        });
        tv.setTextColor(Color.WHITE);
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        view.setBackgroundColor(themeColor);
    }


    void readFile(){
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                dataSet.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void saveFile(){
        clearInfoForFile(filename);
        //写入中文字符时解决中文乱码问题
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(new File(filename));
            OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw=new BufferedWriter(osw);
            for(String arr:dataSet){
                bw.write(arr+"\t\n");
            }
            //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
            bw.close();
            osw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    void addTag(String tag_str){
        dataSet.add(tag_str);
        saveFile();
        notifyDataSetChanged();
    }
    void deleteTag(String key){
        Iterator<String> it = dataSet.iterator();
        while(it.hasNext()){
            String str = (String)it.next();
            if(key.equals(str)){
                it.remove();
            }
        }
        saveFile();
        notifyDataSetChanged();
    }
    void deleteTag(int index){
        dataSet.remove(index);
        saveFile();
        notifyDataSetChanged();
    }
    void clear(){
        dataSet.clear();
        saveFile();
        notifyDataSetChanged();
    }
    String ramdonGet(){
        if(dataSet.size()==0) return "";
        Random random=new Random();
        int random_index=random.nextInt(dataSet.size());
        return dataSet.get(random_index);
    }

    // 清空已有的文件内容，以便下次重新写入新的内容
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
