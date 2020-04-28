/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.uioverrides.states;

import android.content.res.Resources;
import android.graphics.Rect;

import com.android.launcher3.Launcher;
import com.android.launcher3.R;
import com.android.launcher3.userevent.nano.LauncherLogProto.ContainerType;
import com.android.quickstep.views.RecentsView;

/**
 * An Overview state that shows the current task in a modal fashion. Modal state is where the
 * current task is shown on its own without other tasks visible.
 */
public class OverviewModalTaskState extends OverviewState {

    private static final int STATE_FLAGS =
            FLAG_DISABLE_RESTORE | FLAG_OVERVIEW_UI | FLAG_DISABLE_ACCESSIBILITY;

    public OverviewModalTaskState(int id) {
        super(id, ContainerType.OVERVIEW, STATE_FLAGS);
    }

    @Override
    public int getTransitionDuration(Launcher launcher) {
        return 100;
    }

    @Override
    public int getVisibleElements(Launcher launcher) {
        return OVERVIEW_BUTTONS;
    }

    @Override
    public float[] getOverviewScaleAndOffset(Launcher launcher) {
        Resources res = launcher.getBaseContext().getResources();

        Rect out = new Rect();
        launcher.<RecentsView>getOverviewPanel().getTaskSize(out);
        int taskHeight = out.height();

        float topMargin = res.getDimension(R.dimen.task_thumbnail_top_margin);
        float bottomMargin = res.getDimension(R.dimen.task_thumbnail_bottom_margin_with_actions);
        float newHeight = taskHeight + topMargin + bottomMargin;
        float scale = newHeight / taskHeight;

        return new float[] {scale, 0};
    }

    @Override
    public float getOverviewModalness() {
        return 1.0f;
    }
}
