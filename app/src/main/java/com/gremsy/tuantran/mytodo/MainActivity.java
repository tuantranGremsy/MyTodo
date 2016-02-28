package com.gremsy.tuantran.mytodo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<TodoTask> todoItems = new ArrayList<TodoTask>();
    TaskAdapter todoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 1;
    TodoItemDatabaseHelper helper_ob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_list);

        helper_ob = new TodoItemDatabaseHelper(this);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                helper_ob.deleteItem(todoItems.get(position));
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent editIntent = new Intent(MainActivity.this,EditTaskActivity.class);
                editIntent.putExtra("dbID", helper_ob.getItemIndex(todoItems.get(position).task));
                editIntent.putExtra("Item",todoItems.get(position));
                editIntent.putExtra("ItemPosition", position);
                startActivityForResult(editIntent,REQUEST_CODE);

            }
        });


    }

    public void populateArrayItems() {
        readItems();
        todoAdapter = new TaskAdapter(this, todoItems);
    }

    private void readItems(){
        todoItems = helper_ob.getAllItems();
    }


    public void onAddItem(View view) {
        if(etEditText.getText().toString().trim().equals("")) {
            Toast toast = Toast.makeText(this, "Item can't be empty", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            todoItems.add(new TodoTask(etEditText.getText().toString(), "MEDIUM"));
            helper_ob.insertItem(etEditText.getText().toString(), "MEDIUM");
            etEditText.setText("");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            todoItems.set(data.getIntExtra("ItemPosition",0), (TodoTask) data.getSerializableExtra("Item"));
            todoAdapter.notifyDataSetChanged();
        }
    }





}
