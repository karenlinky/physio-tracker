package com.kykarenlin.physiotracker.ui.eventlogs.filters;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kykarenlin.physiotracker.ui.eventlogs.EventWrapped;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FilterKeyword extends EventFilter{

    private static String keyword = "";
    public final static String KEYWORD_DELIMETER = "[\\s\\p{Punct}]+";

    public void notifyKeywordChanged() {
        FilterKeyword.keyword = this.getUIElement().getText().toString().trim().toLowerCase();
    }
    @Override
    public boolean matchesCondition(EventWrapped eventWrapped) {
        String[] lstStrKeyword = FilterKeyword.keyword.split(KEYWORD_DELIMETER);
        Set<String> setStrKeywords = new HashSet<>(Arrays.asList(lstStrKeyword));
        Set<String> setStrDetails = eventWrapped.getSetStrDetails();

        for (String strKeyword : setStrKeywords) {
            for (String strDetails : setStrDetails) {
                if (strDetails.contains(strKeyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public EditText getUIElement() {
        return FilterManager.getKeywordFilterUI();
    }

//    @Override
//    public FilterKeyword getFilter() {
//        return FilterManager.getKeywordFilter();
//    }

    @Override
    public void updateUIElement() {
        this.getUIElement().setText(this.getIsActivated() ? FilterKeyword.keyword : "");
    }
}
