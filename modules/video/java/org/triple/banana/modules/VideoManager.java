// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.modules;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.WindowManager;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.ApplicationStatus;

@JNINamespace("modules")
class VideoManager {
    private long mNativeVideoManager;

    private VideoManager(long nativeVideoManager) {
	mNativeVideoManager = nativeVideoManager;
    }

    @CalledByNative
    private static VideoManager create(long nativeVideoManager) {
        return new VideoManager(nativeVideoManager);
    }

    @CalledByNative
    private void showVideo() {
	Activity activity = ApplicationStatus.getLastTrackedFocusedActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.show();
	android.util.Log.i("HELLO", "GOODJOB");
    }
}
