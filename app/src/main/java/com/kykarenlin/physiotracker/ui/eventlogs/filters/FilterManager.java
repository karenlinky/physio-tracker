package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.kykarenlin.physiotracker.R;
import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterManager {
    private static FilterManager instance = null;
    private static Set<EventFilter> activatedFilters = new HashSet<>();

    private static FilterKeyword filterKeyword = new FilterKeyword();
    private static FilterImportant filterImportant = new FilterImportant();
    private static FilterPainDiscomfort filterPainDiscomfort = new FilterPainDiscomfort();
    private static Set<EventFilter> allFilters = new HashSet<>(Arrays.asList(filterKeyword, filterImportant, filterPainDiscomfort));

    private int activatedBgColorId;
    private int notActivatedBgColorId;

    private static EditText edtFilterKeyword;
    private static Chip chpFilterImportance;
    private static Chip chpFilterPainDiscomfort;

    public FilterManager(Context context, EditText edtFilterKeyword, Chip chpFilterImportance, Chip chpFilterPainDiscomfort) {
        TypedValue activatedBgTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorChipBackgroundActivated, activatedBgTypedValue, true);
        this.activatedBgColorId = activatedBgTypedValue.resourceId;

        TypedValue notActivatedBgTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorChipBackground, notActivatedBgTypedValue, true);
        this.notActivatedBgColorId = notActivatedBgTypedValue.resourceId;

        FilterManager.edtFilterKeyword = edtFilterKeyword;
        FilterManager.chpFilterImportance = chpFilterImportance;
        FilterManager.chpFilterPainDiscomfort = chpFilterPainDiscomfort;

        updateAllFilterUIElements();
    }

    public List<EventWrapped> filter(List<EventWrapped> eventsWrapped) {
        if (FilterManager.noFilterActivated()) {
            return eventsWrapped;
        }
        List<EventWrapped> filteredList = new ArrayList<>();
        for (EventWrapped eventWrapped : eventsWrapped) {
            for (EventFilter eventFilter : activatedFilters) {
                if (eventFilter.matchesCondition(eventWrapped)) {
                    filteredList.add(eventWrapped);
                    break;
                }
            }
        }
        return filteredList;
    }

    public void updateFilter(EventFilter eventFilter, boolean activated) {
        if (activated) {
            activatedFilters.add(eventFilter);
        } else {
            activatedFilters.remove(eventFilter);
        }
    }


    private void updateUIElements(EventFilter eventFilter) {
        eventFilter.updateUIElement();
    }

    private void updateAllFilterUIElements() {
        for (EventFilter eventFilter : FilterManager.allFilters) {
            this.updateUIElements(eventFilter);
        }
    }

    private static boolean noFilterActivated() {
        return FilterManager.activatedFilters.isEmpty();
    }

    public static FilterKeyword getKeywordFilter() {
        return FilterManager.filterKeyword;
    }

    public static FilterImportant getImportantFilter() {
        return FilterManager.filterImportant;
    }

    public static FilterPainDiscomfort getPainDiscomfortFilter() {
        return FilterManager.filterPainDiscomfort;
    }

    public static boolean getFilterActivated(EventFilter eventFilter) {
        return FilterManager.activatedFilters.contains(eventFilter);
    }

//    public static boolean getImportantFilterActivated() {
//        return FilterManager.activatedFilters.contains(FilterManager.filterImportant);
//    }
//
//    public static boolean getPainDiscomfortFilterActivated() {
//        return FilterManager.activatedFilters.contains(FilterManager.filterPainDiscomfort);
//    }

    public static EditText getKeywordFilterUI() {
        return FilterManager.edtFilterKeyword;
    }

    public static Chip getImportanceFilterUI() {
        return FilterManager.chpFilterImportance;
    }

    public static Chip getPainDiscomfortFilterUI() {
        return FilterManager.chpFilterPainDiscomfort;
    }
}
