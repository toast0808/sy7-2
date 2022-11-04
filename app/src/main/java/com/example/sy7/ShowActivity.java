package com.example.sy7;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends AppCompatActivity {


    private List<Contacts> contactsList=new ArrayList<>();

    private String newId;


    private EditText nameEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        getSupportActionBar().setTitle("内容提供器test");



        ContactsAdapter adapter=new ContactsAdapter(ShowActivity.this,R.layout.book_item, contactsList);
        ListView listView=(ListView)findViewById((R.id.list_view));
        listView.setAdapter(adapter);

        nameEdit=(EditText)findViewById(R.id.inputname);

      //  Button addData=(Button)findViewById(R.id.add_data);
      //  addData.setOnClickListener(new View.OnClickListener(){
      //      @Override
       //     public void onClick(View v) {
       //         Uri uri=Uri.parse("content://com.example.sy7.provider/contacts");
       //         ContentValues values=new ContentValues();
       //         values.put("name","陈六");
       //         values.put("number","15647891234");
       //         values.put("sex","女");
        //        Uri newUri=getContentResolver().insert(uri,values);
        //        newId= newUri.getPathSegments().get(1);
      //      }
      //  });



       // dbHelper=new com.example.sy7.MyDatabaseHelper(this,"db.db",null,2);

      //  Button addBook=(Button)findViewById(R.id.add_book);

       // addBook.setOnClickListener(new View.OnClickListener(){
        //    @Override
        //    public void onClick(View view) {
         //       Intent intent=new Intent(ShowActivity.this, com.example.sy7.AddBookActivity.class);

         //       startActivity(intent);

         //   }
       // });

         //Button queryButton = (Button) findViewById(R.id.query);
       // queryButton.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View view) {
        //String inputname=nameEdit.getText().toString();
        //String inputname="王五";


        // Button queryButton = (Button) findViewById(R.id.query);
       // queryButton.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View v) {

               Uri uri = Uri.parse("content://com.example.sy7.provider/contacts");
               String inputname = nameEdit.getText().toString();

               // Log.d("ShowActivity",inputname);

               //Cursor cursor=getContentResolver().query(uri,new String[]{"name, number, sex"}, "name=?", new String[]{"王五"},null);
               Cursor cursor = getContentResolver().query(uri, null, null, null, null);

              if(cursor!=null){

               while (cursor.moveToNext()) {
                   @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                   @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("number"));
                   @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));

                   Contacts b = new Contacts(name, number, sex);
                   contactsList.add(b);

               }
               cursor.close();
           }

           }
     //    });

   // }

}

class Contacts {
    private String name;
    private String number;
    private String sex;

    public Contacts(String name, String number, String sex) {
        this.name = name;
        this.number = number;
        this.sex = sex;
    }
    public String getName() {return name;}
    public String getNumber() { return number;}
    public String getSex() {return sex;}

}

class ContactsAdapter extends ArrayAdapter<com.example.sy7.Contacts> {
    private int resourceId;
    public ContactsAdapter(Context context, int textViewResourceId,
                       List<com.example.sy7.Contacts> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        com.example.sy7.Contacts contacts=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null) {
            view= LayoutInflater.from(getContext()).inflate(resourceId, parent,
                    false);
            viewHolder=new ViewHolder();
            viewHolder.contactsName=(TextView)view.findViewById(R.id.contacts_Name);
            viewHolder.contactsNumber=(TextView)view.findViewById(R.id.contacts_Number);
            viewHolder.contactsSex=(TextView)view.findViewById(R.id.contacts_Sex);
            view.setTag(viewHolder);
        }else{
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.contactsName.setText(contacts.getName());
        viewHolder.contactsNumber.setText(contacts.getNumber());
        viewHolder.contactsSex.setText(contacts.getSex());
        //TextView pcName=(TextView)view.findViewById(R.id.pc_name);
        //pcName.setText(pc.getName());
        return view;
    }

    class ViewHolder{
        TextView contactsName;
        TextView contactsNumber;
        TextView contactsSex;
    }
}