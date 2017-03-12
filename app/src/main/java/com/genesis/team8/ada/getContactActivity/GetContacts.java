package com.genesis.team8.ada.getContactActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.genesis.team8.ada.R;

/**
 * Created by asif ali on 14/01/17.
 */

public class GetContacts extends AppCompatActivity {

    Button SelectContact1,SelectContact2,SelectContact3,SelectContact4,SelectContact5,Reset;
    TextView DispName1,DispName2,DispName3,DispName4,DispName5;
    private int Selection1=1,Selection2=2,Selection3=3,Selection4=4,Selection5=5;
    String name1="name1",name2="name2",name3="name3",name4="name4",name5="name5",number1="number1",number2="number2",number3="number3",number4="number4",number5="number5";
    SharedPreferences details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_contacts);

        SelectContact1=(Button)findViewById(R.id.button_contact_one);
        SelectContact2=(Button)findViewById(R.id.button_contact_two);
        SelectContact3=(Button)findViewById(R.id.button_contact_three);
        SelectContact4=(Button)findViewById(R.id.button_contact_four);
        SelectContact5=(Button)findViewById(R.id.button_contact_five);
        Reset=(Button)findViewById(R.id.button_reset);
        details= PreferenceManager.getDefaultSharedPreferences(this);
        DispName1=(TextView)findViewById(R.id.contact_one);
        DispName2=(TextView)findViewById(R.id.contact_two);
        DispName3=(TextView)findViewById(R.id.contact_three);
        DispName4=(TextView)findViewById(R.id.contact_four);
        DispName5=(TextView)findViewById(R.id.contact_five);
        DispName1.setText(details.getString(name1,"Enter Contact"));
        DispName2.setText(details.getString(name2,"Enter Contact"));
        DispName3.setText(details.getString(name3,"Enter Contact"));
        DispName4.setText(details.getString(name4,"Enter Contact"));
        DispName5.setText(details.getString(name5,"Enter Contact"));
        SelectContact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContacts(Selection1);
            }
        });
        SelectContact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContacts(Selection2);
            }
        });
        SelectContact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContacts(Selection3);
            }
        });
        SelectContact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContacts(Selection4);
            }
        });
        SelectContact5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenContacts(Selection5);
            }
        });
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=details.edit();
                editor.clear();
                editor.commit();
                DispName1.setText("Enter Contact");
                DispName2.setText("Enter Contact");
                DispName3.setText("Enter Contact");
                DispName4.setText("Enter Contact");
                DispName5.setText("Enter Contact");
            }
        });



    }

    private void  OpenContacts(int n)
    {
        Intent i=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(i, n);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Selection1<=requestCode&&requestCode<=Selection5)
        {
            if(resultCode==RESULT_OK)
            {
                SharedPreferences.Editor edit=details.edit();
                Uri uri= data.getData();
                String p[]= {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                Cursor cursor= getContentResolver()
                        .query(uri, p, null, null, null);
                cursor.moveToFirst();
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(column);
                cursor.close();
                String p1[]= {ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor1= getContentResolver()
                        .query(uri, p1, null, null, null);
                cursor1.moveToFirst();
                column = cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor1.getString(column);
                cursor1.close();
                switch(requestCode)
                {
                    case 1:
                        DispName1.setText(name);
                        edit.putString(name1, name);
                        edit.apply();
                        edit.putString(number1,number);
                        edit.apply();
                        break;
                    case 2:
                        DispName2.setText(name);
                        edit.putString(name2, name);
                        edit.apply();
                        edit.putString(number2,number);
                        edit.apply();
                        break;
                    case 3:
                        DispName3.setText(name);
                        edit.putString(name3, name);
                        edit.apply();
                        edit.putString(number3,number);
                        edit.apply();
                        break;
                    case 4:
                        DispName4.setText(name);
                        edit.putString(name4, name);
                        edit.apply();
                        edit.putString(number4,number);
                        edit.apply();
                        break;
                    case 5:
                        DispName5.setText(name);
                        edit.putString(name5, name);
                        edit.apply();
                        edit.putString(number5,number);
                        edit.apply();
                        break;
                }


            }
        }
    }


}
