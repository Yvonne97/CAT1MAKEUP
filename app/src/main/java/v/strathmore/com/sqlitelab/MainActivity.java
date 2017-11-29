package v.strathmore.com.sqlitelab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD OPERATIONS
         */
        //inserting contacts
        Log.d("Insert: ", "Inserting...");
        db.addContact(new Contacts("Jeff", "9100000000"));
        db.addContact(new Contacts("Elinor", "9199999999"));
        db.addContact(new Contacts("Ron", "9522222222"));
        db.addContact(new Contacts("Sheila", "9533333333"));

        //reading all contacts
        Log.d("Reading: ", "Reading all contacts...");
        List <Contacts> contacts = db.getAllContacts();

        for (Contacts cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: "+cn.getName() +
                    " ,Phone: " +cn.getPhoneNumber();
            //writing contacts to log
            Log.d("Name: ", log);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class AndroidSQLiteTutorialActivity extends Activity {

        @Override
        public void onCreate (Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

        }
    }
}
