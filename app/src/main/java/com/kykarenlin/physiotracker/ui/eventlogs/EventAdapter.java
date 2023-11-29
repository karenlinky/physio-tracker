package com.kykarenlin.physiotracker.ui.eventlogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.enums.EventImprovementStatus;
import com.kykarenlin.physiotracker.model.event.Event;
import com.kykarenlin.physiotracker.utils.DateTimeHelper;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private List<EventWrapped> eventsWrapped = new ArrayList<>();

    private OnItemClickListener listener;

    private Context context;
    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        EventWrapped currentWrapped = eventsWrapped.get(position);
        Event event = currentWrapped.getEvent();
        String strStartDate = DateTimeHelper.toStringDateWithDay(event.getEventStartTime());
        String strEndDate = DateTimeHelper.toStringDateWithDay(event.getEventEndTime());
        holder.txtEventDate.setText(strStartDate);
        holder.txtEventDetails.setText(event.getEventDetails());
//        holder.txtDatePeriod.setText("");
        holder.txtDatePeriod.setVisibility(View.GONE);
//        holder.txtDatePeriod.setText(strStartDate);

//        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//        boolean isNightMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
//
//        int painColor = ContextCompat.getColor(context, R.color.eventWithPain);
//        int withoutPainColor = ContextCompat.getColor(context, R.color.trackerListEnabled);
//
//        if (isNightMode) {
//            painColor = ContextCompat.getColor(context, R.color.eventWithPainDark);
//            withoutPainColor = ContextCompat.getColor(context, R.color.trackerListEnabledDark);
//        }

        if (event.isPainOrDiscomfort()) {
            holder.painIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.painIndicator.setVisibility(View.GONE);
        }

        if (currentWrapped.shouldShowDate()) {
            holder.txtEventDate.setVisibility(View.VISIBLE);
        } else {
            holder.txtEventDate.setVisibility(View.GONE);
        }

        if (currentWrapped.shouldShowEndOfWeekIndicator()) {
            holder.viewWeekSeparator.setVisibility(View.VISIBLE);
        } else {
            holder.viewWeekSeparator.setVisibility(View.GONE);
        }

        if (event.isActivity()) {
            holder.icActive.setVisibility(View.VISIBLE);
        } else {
            holder.icActive.setVisibility(View.GONE);
        }

        if (event.getImprovementStatus().equals(EventImprovementStatus.IMPROVED.toString())) {
            holder.icUp.setVisibility(View.VISIBLE);
            holder.icDown.setVisibility(View.GONE);
        } else if (event.getImprovementStatus().equals(EventImprovementStatus.WORSENED.toString())) {
            holder.icUp.setVisibility(View.GONE);
            holder.icDown.setVisibility(View.VISIBLE);
        } else {
            holder.icUp.setVisibility(View.GONE);
            holder.icDown.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return eventsWrapped.size();
    }

    public void setEventsWrapped(List<EventWrapped> eventsWrapped) {
        this.eventsWrapped = eventsWrapped;
        notifyDataSetChanged();
    }

    class EventHolder extends RecyclerView.ViewHolder {
        private ImageView painIndicator;
        private TextView txtEventDate;
        private TextView txtEventDetails;
        private TextView txtDatePeriod;
        private CardView icActive;
        private CardView icUp;
        private CardView icDown;

        private View viewWeekSeparator;

        public EventHolder(View itemView) {
            super(itemView);
            painIndicator= itemView.findViewById(R.id.painIndicator);
            txtEventDate = itemView.findViewById(R.id.txtEventDate);
            txtEventDetails = itemView.findViewById(R.id.txtEventDetails);
            txtDatePeriod = itemView.findViewById(R.id.txtDatePeriod);
            icActive = itemView.findViewById(R.id.icActive);
            icUp = itemView.findViewById(R.id.icUp);
            icDown = itemView.findViewById(R.id.icDown);
            viewWeekSeparator = itemView.findViewById(R.id.viewWeekSeparator);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(eventsWrapped.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventWrapped event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
