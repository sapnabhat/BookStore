package edu.stevens.cs522.bookstorewithprovider.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.stevens.cs522.bookstorewithprovider.R;

public class CheckoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //ORDER and CANCEL options.
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_checkout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.order:
                EditText creditInfo = (EditText) findViewById(R.id.credit);
                EditText nameInfo = (EditText) findViewById(R.id.name);
                EditText emailInfo = (EditText) findViewById(R.id.email);
                EditText addressInfo = (EditText) findViewById(R.id.address);
                String creditStr = creditInfo.getText().toString();
                String nameStr = nameInfo.getText().toString();
                String emailStr = emailInfo.getText().toString();
                String addressStr = addressInfo.getText().toString();
                if(creditStr.isEmpty()||nameStr.isEmpty()||emailStr.isEmpty()||addressStr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter all Details", Toast.LENGTH_LONG).show();
                    return true;
                }
                else{
                    Toast.makeText(getApplicationContext(),"Order has been placed", Toast.LENGTH_LONG).show();
                    setResult(2);
                    finish();
                }
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return false;
    }
}
