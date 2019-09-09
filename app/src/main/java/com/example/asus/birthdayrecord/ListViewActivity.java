package com.example.asus.birthdayrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewActivity extends AppCompatActivity {
    private TextView birthTextView;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Birthday List");
        setContentView(R.layout.activity_list_view);

        lv = (ListView) findViewById(R.id.listViewId);
        MyDBFunctions myDBFunctions= new MyDBFunctions(getApplicationContext());

        String[] data = myDBFunctions.showData();
        lv.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.single_item,R.id.singleItemId,data));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Nothing to do",Toast.LENGTH_LONG).show();
            }
        });

      /*  String s = "";

        for(int i = 0; i<data.length; i++){
            s = s + data[i]+"\n\n";
        }
        birthTextView.setText(s);*/
    }
}
