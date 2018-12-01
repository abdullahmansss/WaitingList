package mans.abdullah.abdullah_mansour.waitinglist;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import mans.abdullah.abdullah_mansour.waitinglist.Data.WaitlistContract.WaitlistEntry;

import mans.abdullah.abdullah_mansour.waitinglist.GuestAdapter.GuestViewHolder;

public class GuestAdapter extends RecyclerView.Adapter<GuestViewHolder>
{
    Context context;
    Cursor cursor;
    WhenItemClick whenItemClick;

    public GuestAdapter(Context context, Cursor cursor)
    {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item , parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position)
    {
        if (!cursor.moveToPosition(position))
        {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(WaitlistEntry.COLUMN_GUEST_NAME));
        String number = cursor.getString(cursor.getColumnIndex(WaitlistEntry.COLUMN_PARTY_SIZE));
        long id = cursor.getLong(cursor.getColumnIndex(WaitlistEntry._ID));

        holder.name.setText(name);
        holder.number.setText(number);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }

    public void swapCursor (Cursor cursor1)
    {
        if (cursor != null)
        {
            cursor.close();
        }

        cursor = cursor1;

        if (cursor1 != null)
        {
            notifyDataSetChanged();
        }

    }

    public class GuestViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,number;

        public GuestViewHolder(View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.guest_name);
            number = itemView.findViewById(R.id.guest_number);

            name.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (whenItemClick != null)
                    {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                        {
                            whenItemClick.onName(position);
                        }
                    }
                }
            });

            number.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (whenItemClick != null)
                    {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION)
                        {
                            whenItemClick.onSize(position);
                        }
                    }
                }
            });
        }
    }

    public interface WhenItemClick
    {
        void onSize(int position);
        void onName(int position);
    }

    public void LamaTedos3alaItem (WhenItemClick whenItemClick)
    {
        this.whenItemClick = whenItemClick;
    }
}
