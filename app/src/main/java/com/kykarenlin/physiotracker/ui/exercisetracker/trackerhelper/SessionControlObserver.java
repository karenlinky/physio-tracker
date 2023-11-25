package com.kykarenlin.physiotracker.ui.exercisetracker.trackerhelper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageButton;

import com.kykarenlin.physiotracker.enums.TrackerStatus;

public class SessionControlObserver extends TrackerObserver{

    private TrackerStatusSubject trackerStatusSubject;
    private ImageButton btnContinueSession;
    private ImageButton btnPauseSession;
    private ImageButton btnFinishSession;
    private ImageButton btnResetSession;
    public SessionControlObserver(TrackerStatusSubject trackerStatusSubject, ImageButton btnContinueSession, ImageButton btnPauseSession , ImageButton btnFinishSession, ImageButton btnResetSession) {
        this.trackerStatusSubject = trackerStatusSubject;
        this.btnContinueSession = btnContinueSession;
        this.btnPauseSession = btnPauseSession;
        this.btnFinishSession = btnFinishSession;
        this.btnResetSession = btnResetSession;
    }

    private void showSessionOngoingButtons(boolean showPauseBtn) {
        if (showPauseBtn) {
            this.btnPauseSession.setVisibility(View.VISIBLE);
            this.btnContinueSession.setVisibility(View.GONE);
        } else {
            this.btnContinueSession.setVisibility(View.VISIBLE);
            this.btnPauseSession.setVisibility(View.GONE);
        }
        this.btnFinishSession.setVisibility(View.VISIBLE);
        this.btnResetSession.setVisibility(View.GONE);
    }

    private void showResetButton() {
        this.btnContinueSession.setVisibility(View.GONE);
        this.btnPauseSession.setVisibility(View.GONE);
        this.btnFinishSession.setVisibility(View.GONE);
        this.btnResetSession.setVisibility(View.VISIBLE);
    }

    private void enableButton(ImageButton btn, String colorStr) {
        btn.setEnabled(true);
        btn.setBackground(new ColorDrawable(Color.parseColor(colorStr)));
    }

    private void disableButton(ImageButton btn) {
        btn.setEnabled(true);
        btn.setBackground(new ColorDrawable(Color.parseColor("#FFE0E0E0")));
    }

    private void updateViews() {
        TrackerStatus status = trackerStatusSubject.getStatus();
        boolean sessionPaused = trackerStatusSubject.getSessionPaused();

        switch (status) {
            case SESSION_NOT_STARTED:
                this.showSessionOngoingButtons(true);
                this.btnPauseSession.setEnabled(false);
                this.btnFinishSession.setEnabled(false);
                break;
            case WORKOUT_IN_PROGRESS:
            case BREAK:
                this.showSessionOngoingButtons(!sessionPaused);
                this.btnContinueSession.setEnabled(sessionPaused);
                this.btnPauseSession.setEnabled(!sessionPaused);
                this.btnFinishSession.setEnabled(true);
                break;
            case SESSION_COMPLETED:
                this.showResetButton();
                break;
        }
    }

    @Override
    public void notifyStateChanged() {
        this.updateViews();
    }
}
