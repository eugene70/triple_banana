// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.remote_control;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

import org.banana.cake.interfaces.BananaTab;
import org.triple.banana.R;
import org.triple.banana.media.MediaController;
import org.triple.banana.media.MediaEventListener;

import org.chromium.chrome.browser.ChromeActivity;
import org.chromium.chrome.browser.media.PictureInPictureController;

public enum RemoteControlService implements RemoteControlView.Delegate {
    instance;

    private RemoteControlViewModel mViewModel = new RemoteControlViewModel();
    private RemoteControlViewImpl mView = new RemoteControlViewImpl(this);
    private boolean mWasPipMode;

    private MediaController mMediaController = MediaController.instance;

    private PictureInPictureController mPictureInPictureController;

    public void start() {
        mViewModel.addListener(mView);
        mMediaController.addEventListener(new MediaEventListener() {
            @Override
            public void onEnteredVideoFullscreen() {
                BananaTab tab = org.banana.cake.interfaces.BananaTabManager.get().getActivityTab();
                if (tab == null || tab.getContext() == null) return;
                mView.show(tab.getContext());
            }

            @Override
            public void onExitedVideoFullscreen() {
                mView.dismiss();
            }

            @Override
            public void onChangedPipMode(boolean value) {
                if (value) {
                    mView.dismiss();
                } else if (mWasPipMode) {
                    onEnteredVideoFullscreen();
                }
                mWasPipMode = value;
            }
        });
    }

    @Override
    public void onCancel() {
        BananaTab tab = org.banana.cake.interfaces.BananaTabManager.get().getActivityTab();
        if (tab == null || tab.getContext() == null) return;
        tab.exitFullscreen();
    }

    @Override
    public void onRemoteControlButtonClicked(int id) {
        if (id == R.id.play_button) {
            mMediaController.play();
        } else if (id == R.id.pause_button) {
            mMediaController.pause();
        } else if (id == R.id.backward_button) {
            mMediaController.setRelativePosition(-10.0f);
        } else if (id == R.id.forward_button) {
            mMediaController.setRelativePosition(10.0f);
        } else if (id == R.id.brightness_up_button) {
            mViewModel.getEditor().setBrightness(1.0f);
            mViewModel.commit();
        } else if (id == R.id.brightness_down_button) {
            mViewModel.getEditor().setBrightness(0.2f);
            mViewModel.commit();
        } else if (id == R.id.volume_down_button) {
            mMediaController.setRelativeVolume(-0.1f);
        } else if (id == R.id.volume_up_button) {
            mMediaController.setRelativeVolume(0.1f);
        } else if (id == R.id.rotate_button) {
            toggleOrientation();
        } else if (id == R.id.lock_button) {
            // NOTIMPLEMENTED
            enterPipMode();
        } else if (id == R.id.seek_bar) {
            // NOTIMPLEMENTED
        }
    }
    private void enterPipMode() {
        BananaTab tab = org.banana.cake.interfaces.BananaTabManager.get().getActivityTab();
        if (tab == null || tab.getContext() == null) return;
        ChromeActivity activity = (ChromeActivity) tab.getContext();
        if (mPictureInPictureController == null) {
            mPictureInPictureController = new PictureInPictureController();
        }
        mPictureInPictureController.attemptPictureInPicture(activity);
    }

    private void toggleOrientation() {
        BananaTab tab = org.banana.cake.interfaces.BananaTabManager.get().getActivityTab();
        if (tab == null || tab.getContext() == null) return;
        Activity activity = (Activity) tab.getContext();
        int orientation = activity.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
