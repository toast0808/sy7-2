package com.example.sy7;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private EditText nameEdit;
    private EditText idEdit;
    private EditText authorEdit;
    private EditText priceEdit;
    private EditText pagesEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook_layout);
        getSupportActionBar().setTitle("添加书本信息");

        nameEdit=(EditText)findViewById(R.id.inputname);
        idEdit=(EditText)findViewById(R.id.inputid);
        authorEdit=(EditText)findViewById(R.id.inputauthor);
        priceEdit=(EditText)findViewById(R.id.inputprice);
        pagesEdit=(EditText)findViewById(R.id.inputpages);


        dbHelper=new MyDatabaseHelper(this,"BookStore.db",null,2);

        Button addin=(Button)findViewById(R.id.add_in);
        addin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //dbHelper.getWritableDatabase();
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();

                String inputname=nameEdit.getText().toString();
                String inputid=idEdit.getText().toString();
                String inputauthor=authorEdit.getText().toString();
                String inputprice=priceEdit.getText().toString();
                String inputpages=pagesEdit.getText().toString();

                //Book表 组装第一条数据（添加数据）
                values.put("name",inputname);
                values.put("author",inputauthor);
                values.put("pages",inputpages);
                values.put("price",inputprice);
                values.put("category_id",inputid);
                db.insert("Book",null,values);
                Toast.makeText(AddBookActivity.this,"添加成功",Toast.LENGTH_LONG).show();
            }
        });


    }
}