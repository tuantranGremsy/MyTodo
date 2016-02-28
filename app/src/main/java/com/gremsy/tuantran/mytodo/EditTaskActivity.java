package com.gremsy.tuantran.mytodo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditTaskActivity extends AppCompatActivity {

    EditText editView;
    Button saveBtn;
    int position, dbIndex;
    String priority;
    TodoItemDatabaseHelper helper;
    TodoTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_list);
        editView = (EditText) findViewById(R.id.editView);
        editView.requestFocus();
        editView.setFocusableInTouchMode(true);
        editView.setFocusable(true);

        // Get intent
        task = (TodoTask) getIntent().getSerializableExtra("Item");
        priority = task.priority;
        setPriorityState(priority);
        editView.setText(task.task);
        editView.setSelection(editView.length());
        editView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        position = getIntent().getIntExtra("ItemPosition",0);
        dbIndex = getIntent().getIntExtra("dbID",0);

        helper = new TodoItemDatabaseHelper(this);

        // Save button
        saveBtn = (Button)findViewById(R.id.saveButton);


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioHigh:
                if (checked)
                    priority = "HIGH";
                    break;
            case R.id.radioMedium:
                if (checked)
                    priority = "MEDIUM";
                    break;
            case R.id.radioLow:
                if (checked)
                    priority = "LOW";
                    break;
        }
    }

    public void saveItem(View view) {
        Intent returnData = new Intent();
        returnData.putExtra("Item", new TodoTask(editView.getText().toString(),priority));
        returnData.putExtra("ItemPosition", position);
        helper.updateItem(dbIndex, editView.getText().toString(), priority);
        setResult(RESULT_OK, returnData);
        finish();
    }
    private void setPriorityState(String priority){
        RadioButton rbu_high =(RadioButton)findViewById(R.id.radioHigh);
        RadioButton rbu_medium =(RadioButton)findViewById(R.id.radioMedium);
        RadioButton rbu_low =(RadioButton)findViewById(R.id.radioLow);
        switch (priority){
            case "HIGH":
                rbu_high.setChecked(true);
                break;
            case "MEDIUM":
                rbu_medium.setChecked(true);
                break;
            case "LOW":
                rbu_low.setChecked(true);
                break;
        }
    }
}
