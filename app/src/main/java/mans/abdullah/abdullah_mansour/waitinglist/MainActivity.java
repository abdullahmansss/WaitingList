package mans.abdullah.abdullah_mansour.waitinglist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import mans.abdullah.abdullah_mansour.waitinglist.Data.WaitlistContract.WaitlistEntry;

import mans.abdullah.abdullah_mansour.waitinglist.Data.WaitlistDBHelper;

public class MainActivity extends AppCompatActivity implements GuestAdapter.WhenItemClick
{
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;

    WaitlistDBHelper waitlistDBHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    GuestAdapter guestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.add_new_user_btn);

        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        waitlistDBHelper = new WaitlistDBHelper(getApplicationContext());
        sqLiteDatabase = waitlistDBHelper.getReadableDatabase();

        cursor = getAllGuests();

        guestAdapter = new GuestAdapter(getApplicationContext(), cursor);

        recyclerView.setAdapter(guestAdapter);

        guestAdapter.LamaTedos3alaItem(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(getApplicationContext(), AddNewUser.class);
                startActivity(n);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,4 | 8)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                guestAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(recyclerView);
    }

    public Cursor getAllGuests ()
    {
        Cursor cursor;

        cursor = sqLiteDatabase.query(
                WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistEntry.COLUMN_TIMESTAMP);

        return cursor;
    }

    public int removeGuest(long id)
    {
        int count;

        count = sqLiteDatabase.delete(
                WaitlistEntry.TABLE_NAME,
                "_id = ?",
                new String[]{Long.toString(id)});

        return count;
    }

    @Override
    public void onSize(int position)
    {
        Toast.makeText(getApplicationContext(), "Size Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onName(int position)
    {
        Toast.makeText(getApplicationContext(), "Name Clicked", Toast.LENGTH_SHORT).show();
    }
}
