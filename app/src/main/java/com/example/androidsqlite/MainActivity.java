package com.example.androidsqlite;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String DATA_BASE_FILE_NAME = "MyDatabase.db";
    private static final String FOLDER_NAME = "My Database Directory";

    private Context context;
    private Button create,read,update,delete;

    private SQLiteAdapter sqLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        context = this;
        create = (Button) findViewById(R.id.create);
        read = (Button) findViewById(R.id.read);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        create.setOnClickListener(this);
        read.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);

        String path_to_store_database = Environment.getExternalStorageDirectory()
                + File.separator + FOLDER_NAME + File.separator + DATA_BASE_FILE_NAME;
        sqLiteAdapter = new SQLiteAdapter(context,path_to_store_database);
    }

    private void createOperation()
    {
        sqLiteAdapter.insertData(2101,"Narel Kumar","Marathahalli Banglore");
    }

    private void readOperation()
    {
        if (sqLiteAdapter != null)
        {
            List<EmployeeModel> employeeModels = sqLiteAdapter.readAllEmployeeDetails();
            for (EmployeeModel employeeModel : employeeModels)
            {
                Log.i("Result","Emp_Id = "+employeeModel.emp_id);
                Log.i("Result","Emp_Name = "+employeeModel.emp_name);
                Log.i("Result","Emp_Address = "+employeeModel.emp_address);
            }
        }
        else
        {
            Toast.makeText(context,"Database doesn't exists",Toast.LENGTH_LONG).show();
        }
    }

    private void updateOperation()
    {
        if (sqLiteAdapter != null)
        {
            if (sqLiteAdapter.updateEmployeeData(2101,"Banglore"))
            {
                Toast.makeText(context,"Updated Successfully!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(context,"Database doesn't exists",Toast.LENGTH_LONG).show();
        }
    }

    private void deleteOperation()
    {
        if (sqLiteAdapter != null)
        {
            if (sqLiteAdapter.deleteEmployeeData(2101))
            {
                Toast.makeText(context,"Deleted Successfully!",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(context,"Database doesn't exists",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.create:
                createOperation();
                break;
            case R.id.read:
                readOperation();
                break;
            case R.id.update:
                updateOperation();
                break;
            case R.id.delete:
                deleteOperation();
                break;
        }
    }
}
