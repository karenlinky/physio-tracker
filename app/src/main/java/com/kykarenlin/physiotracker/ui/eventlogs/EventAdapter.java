package com.kykarenlin.physiotracker.ui.eventlogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private OnItemClickListener clicklistener;

    private OnItemLongClickListener longClicklistener;

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
        long startDate = event.getEventStartTime();
        long endDate = event.getEventEndTime();
        String strStartDate = DateTimeHelper.toStringDateWithDay(startDate);
        String strEndDate = DateTimeHelper.toStringDateWithDay(endDate);
        holder.txtEventDate.setText(strStartDate);
        holder.txtEventDetails.setText(event.getEventDetails());
//        holder.txtDatePeriod.setVisibility(View.GONE);
//        holder.txtDatePeriod.setText(strStartDate);
        if (event.hasStartDate()) {
            holder.txtDatePeriod.setVisibility(View.VISIBLE);
            if (startDate == endDate) {
                holder.txtDatePeriod.setText(DateTimeHelper.toStringTime(startDate));
            } else if (DateTimeHelper.isWithinSameDay(startDate, endDate)) {
                holder.txtDatePeriod.setText(DateTimeHelper.toStringTime(startDate) + " - " + DateTimeHelper.toStringTime(endDate));
            } else {
                holder.txtDatePeriod.setText(DateTimeHelper.toStringDateWithTime(startDate) + " - " + DateTimeHelper.toStringDateWithTime(endDate));
            }
        } else {
            holder.txtDatePeriod.setVisibility(View.GONE);
        }

        if (event.isImportant()) {
            holder.importantIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.importantIndicator.setVisibility(View.GONE);
        }

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

        if (event.isExercise()) {
            holder.icExercise.setVisibility(View.VISIBLE);
        } else {
            holder.icExercise.setVisibility(View.GONE);
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
        private ImageView importantIndicator;
        private TextView txtEventDate;
        private TextView txtEventDetails;
        private TextView txtDatePeriod;

        private CardView icExercise;
        private CardView icActive;
        private CardView icUp;
        private CardView icDown;

        private LinearLayout cardEvent;

        private View viewWeekSeparator;

        public EventHolder(View itemView) {
            super(itemView);
            painIndicator = itemView.findViewById(R.id.painIndicator);
            importantIndicator = itemView.findViewById(R.id.importantIndicator);
            txtEventDate = itemView.findViewById(R.id.txtEventDate);
            txtEventDetails = itemView.findViewById(R.id.txtEventDetails);
            txtDatePeriod = itemView.findViewById(R.id.txtDatePeriod);
            icExercise = itemView.findViewById(R.id.icExercise);
            icActive = itemView.findViewById(R.id.icActive);
            icUp = itemView.findViewById(R.id.icUp);
            icDown = itemView.findViewById(R.id.icDown);
            viewWeekSeparator = itemView.findViewById(R.id.viewWeekSeparator);
            cardEvent = itemView.findViewById(R.id.cardEvent);

            cardEvent.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clicklistener != null && position != RecyclerView.NO_POSITION) {
                    clicklistener.onItemClick(eventsWrapped.get(position));
                }
            });

            cardEvent.setOnLongClickListener(view -> {
                int position = getAdapterPosition();
                if (longClicklistener != null && position != RecyclerView.NO_POSITION) {
                    longClicklistener.onItemLongClick(eventsWrapped.get(position), cardEvent);
                }
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EventWrapped event);
    }

    public void setOnItemClickListener(OnItemClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(EventWrapped event, View itemView);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClicklistener) {
        this.longClicklistener = longClicklistener;
    }
}
