package mans.abdullah.abdullah_mansour.waitinglist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mans.abdullah.abdullah_mansour.waitinglist.Data.WaitlistDBHelper;
import mans.abdullah.abdullah_mansour.waitinglist.Data.WaitlistContract.WaitlistEntry;

public class AddNewUser extends AppCompatActivity
{
    EditText guestname,guestsize;
    Button button;

    WaitlistDBHelper waitlistDBHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        guestname = findViewById(R.id.guest_name_field);
        guestsize = findViewById(R.id.guest_number_field);
        button = findViewById(R.id.save_user_btn);

        waitlistDBHelper = new WaitlistDBHelper(getApplicationContext());
        sqLiteDatabase = waitlistDBHelper.getWritableDatabase();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = guestname.getText().toString();
                String size = guestsize.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(size))
                {
                    Toast.makeText(getApplicationContext(), "please enter a valid data", Toast.LENGTH_SHORT).show();
                } else
                    {
                        addNewGuest(name, size);

                        Intent n = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(n);
                    }
            }
        });

    }

    public long addNewGuest (String name, String size)
    {
        long id;

        ContentValues contentValues = new ContentValues();
        contentValues.put(WaitlistEntry.COLUMN_GUEST_NAME, name);
        contentValues.put(WaitlistEntry.COLUMN_PARTY_SIZE, size);

        id = sqLiteDatabase.insert(
                WaitlistEntry.TABLE_NAME,
                null,
                contentValues);

        return id;
    }
}
