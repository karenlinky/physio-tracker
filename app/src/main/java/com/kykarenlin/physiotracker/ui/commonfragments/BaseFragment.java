package com.kykarenlin.physiotracker.ui.commonfragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) return;
        postponeEnterTransition();
        view.post(() -> postponeEnterTransition(0, TimeUnit.MILLISECONDS));
    }
}
