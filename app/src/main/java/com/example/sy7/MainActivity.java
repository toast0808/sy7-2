package com.example.sy7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Contacts> contactsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportActionBar().setTitle("联系人列表");

        ListView contactsView=(ListView) findViewById(R.id.contacts_view);
        ContactsAdapter adapter=new ContactsAdapter(MainActivity.this, R.layout.contacts_item,contactsList);
        contactsView.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS},1);
        }else{
            readContacts();
        }
    }
    private void readContacts(){
        Cursor cursor=null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if(cursor!=null){
                while(cursor.moveToNext()){
                    @SuppressLint("Range") String displayName=cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    @SuppressLint("Range") String number=cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contacts c=new Contacts(displayName,number);
                    contactsList.add(c);
                }while ((cursor.moveToNext()));
                //adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }
    }

    static class Contacts {
        private String displayName;
        private  String number;

        public Contacts(String displayName, String number) {
            this.displayName = displayName;
            this.number = number;
        }
        public String getDisplayName() {return displayName;}
        public String getNumber() { return number;}

    }


    class ContactsAdapter extends ArrayAdapter<Contacts> {
        private int resourceId;
        public ContactsAdapter(Context context, int textViewResourceId,
                           List<Contacts> objects){
            super(context,textViewResourceId,objects);
            resourceId=textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Contacts contacts= (Contacts) getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView==null) {
                view= LayoutInflater.from(getContext()).inflate(resourceId, parent,
                        false);
                viewHolder=new ViewHolder();
                viewHolder.displayName=(TextView)view.findViewById(R.id.contacts_name);
                viewHolder.number=(TextView)view.findViewById(R.id.contacts_number);
                view.setTag(viewHolder);
            }else{
                view=convertView;
                viewHolder=(ViewHolder)view.getTag();
            }
            viewHolder.displayName.setText(contacts.getDisplayName());
            viewHolder.number.setText(contacts.getNumber());
            //TextView pcName=(TextView)view.findViewById(R.id.pc_name);
            //pcName.setText(pc.getName());
            return view;
        }

        class ViewHolder{
            TextView displayName;
            TextView number;
        }
    }


    public void onRequestPermissionResult(int requestCode,String[]permissions,int[]grantResults){
        switch(requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    readContacts();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
