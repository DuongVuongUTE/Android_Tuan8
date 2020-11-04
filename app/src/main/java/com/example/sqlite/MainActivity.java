package com.example.sqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BaiHocHelper baiHocHelper;
    ListView listView;
    ArrayList<BaiHoc> arrayList;
    BaiHocAdapter adapter;
    EditText edtThem;
    Button btnThem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lvNoiDung);
        edtThem = (EditText) findViewById(R.id.edtThem);
        btnThem = (Button) findViewById(R.id.btnThem);


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bai = edtThem.getText().toString().trim();
                if (TextUtils.isEmpty(bai)){
                    Toast.makeText(MainActivity.this, "Vui lòng nhạp dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                baiHocHelper.QueryData("INSERT INTO NoiDung VALUES (null, '"+ bai +"')");
                actionGetData();
            }
        });

        arrayList = new ArrayList<>();
        adapter = new BaiHocAdapter(this, R.layout.dong_noi_dung, arrayList);
        listView.setAdapter(adapter);

        //tạo database
        baiHocHelper = new BaiHocHelper(this, "BaiHoc.sqlite", null, 1);
        //tạo bảng

        baiHocHelper.QueryData("CREATE TABLE IF NOT EXISTS NoiDung(Id INTEGER PRIMARY KEY AUTOINCREMENT, tenNoiDung VARCHAR(200))");

        //thêm dữ liệu
        //baiHocHelper.QueryData("INSERT INTO NoiDung VALUES (null, 'Bài 1: Giới thiệu vè Android')");
        //baiHocHelper.QueryData("INSERT INTO NoiDung VALUES (null, 'Bài 2: Cài đặt môi trường lập trình Android')");
        //baiHocHelper.QueryData("INSERT INTO NoiDung VALUES (null, 'Bài 3: Tạo project HelloWorld')");
        //baiHocHelper.QueryData("INSERT INTO NoiDung VALUES (null, 'Bài 4: Các thành phần giao diện cơ bản')");

        actionGetData();
    }

    private void actionGetData() {
        //hiển thị
        Cursor data = baiHocHelper.GetData("SELECT * FROM NoiDung");
        arrayList.clear();// xoá mảng trước khi add để cập nhật lại dữ liệu mới thôi

        while (data.moveToNext()){
            String tenBai = data.getString(1);
            int id = data.getInt(0);
            //Toast.makeText(this, tenBai, Toast.LENGTH_SHORT).show();
            arrayList.add(new BaiHoc(id, tenBai));
        }
        adapter.notifyDataSetChanged();
    }

    public void DialogUpdate(final int id, final String ten){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);

        final EditText edtSua = (EditText) dialog.findViewById(R.id.edtCapNhat);
        Button btnSua = (Button) dialog.findViewById(R.id.btnSua);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        edtSua.setText(ten);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtSua.getText().toString().trim();
                baiHocHelper.QueryData("UPDATE NoiDung SET tenNoiDung ='"+ tenMoi +"' WHERE Id = '"+ id +"' ");
                dialog.dismiss();
                actionGetData();
            }
        });
        dialog.show();
    }

    public void DialogDelete(final int id, String ten){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có đồng ý xoá "+ ten +"?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                baiHocHelper.QueryData("DELETE FROM NoiDung WHERE Id = '"+ id +"'");
                actionGetData();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

}
