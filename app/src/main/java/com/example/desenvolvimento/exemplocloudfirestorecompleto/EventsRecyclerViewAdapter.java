package com.example.desenvolvimento.exemplocloudfirestorecompleto;

import android.content.Context;

import com.google.firebase.firestore.FirebaseFirestore;

public class EventsRecyclerViewAdapter extends
        RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder> {

    private List<Event> eventsList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public EventsRecyclerViewAdapter(List<Event> list, Context ctx, FirebaseFirestore firestore) {
        eventsList = list;
        context = ctx;
        firestoreDB = firestore;
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public EventsRecyclerViewAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_event_item, parent, false);

        EventsRecyclerViewAdapter.ViewHolder viewHolder =
                new EventsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EventsRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final Event event = eventsList.get(position);
        holder.name.setText(event.getName());
        holder.place.setText(event.getPlace());
        holder.startTime.setText("" + event.getStartTime());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEventFragment(event);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent(event.getId(), itemPos);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView place;
        public TextView startTime;

        public Button edit;
        public Button delete;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name_tv);
            place = (TextView) view.findViewById(R.id.place_tv);
            startTime = (TextView) view.findViewById(R.id.start_time_tv);

            edit = view.findViewById(R.id.edit_event_b);
            delete = view.findViewById(R.id.delete_event_b);
        }
    }

    private void editEventFragment(Event event) {
        FragmentManager fm = ((EventActivity) context).getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putParcelable("event", event);

        AddEventFragment addFragment = new AddEventFragment();
        addFragment.setArguments(bundle);

        fm.beginTransaction().replace(R.id.events_content, addFragment).commit();
    }

    private void deleteEvent(String docId, final int position) {
        firestoreDB.collection("events").document(docId).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        eventsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, eventsList.size());
                        Toast.makeText(context,
                                "Event document has been deleted",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}