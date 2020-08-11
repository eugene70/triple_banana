// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.remote_control;

import android.content.Context;

import androidx.annotation.NonNull;

interface RemoteControlView {
    static interface Delegate {
        void onCancel();
        void onRemoteControlButtonClicked(int id);
    }

    void show(@NonNull Context context);
    void dismiss();
    void setBrightness(float value);
}