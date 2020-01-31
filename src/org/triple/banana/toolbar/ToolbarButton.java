// Copyright 2020 The Triple Banana Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.triple.banana.toolbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.banana.cake.interfaces.BananaContextUtils;
import org.triple.banana.R;

import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.chrome.browser.ActivityTabProvider;
import org.chromium.chrome.browser.ActivityTabProvider.ActivityTabTabObserver;
import org.chromium.chrome.browser.ThemeColorProvider;
import org.chromium.chrome.browser.ThemeColorProvider.TintObserver;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.ui.widget.ChromeImageButton;

/**
 * The Toolbar button.
 */
class ToolbarButton extends LinearLayout implements TintObserver {
    /**
     * A provider that notifies components when the theme color changes.
     */

    private ButtonId mButtonId;
    private ThemeColorProvider mThemeColorProvider;
    private ActivityTabTabObserver mActivityTabTabObserver;
    private ButtonState mButtonStateListener;

    private LinearLayout mToolbarWrapper;
    private ChromeImageButton mImageButton;
    private TextView mTextView;

    public ToolbarButton(Context context) {
        this(context, null);
    }

    public ToolbarButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolbarButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setButtonId(ButtonId buttonId) {
        this.mButtonId = buttonId;
    }

    public ButtonId getButtonId() {
        return mButtonId;
    }

    private void init() {
        LayoutInflater.from(BananaContextUtils.get().getApplicationContext())
                .inflate(R.layout.toolbar_button, this, true);

        mToolbarWrapper = findViewById(R.id.toolbar_button_wrapper);
        mImageButton = findViewById(R.id.image_button);
        mTextView = findViewById(R.id.button_label);
    }

    public LinearLayout getToolbarWrapper() {
        return mToolbarWrapper;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setToolbarButtonText(String str) {
        mTextView.setText(str);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setImageResource(@DrawableRes int resId) {
        mImageButton.setImageResource(resId);
        mImageButton.setImageTintList(ColorStateList.valueOf(Color.BLACK));
    }

    public void setImageColor(int color) {
        mImageButton.setColorFilter(color);
    }

    public ChromeImageButton getImageButton() {
        return mImageButton;
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    public void setThemeColorProvider(ThemeColorProvider themeColorProvider) {
        mThemeColorProvider = themeColorProvider;
        mThemeColorProvider.addTintObserver(this);
    }

    public void setActivityTabProvider(ActivityTabProvider activityTabProvider) {
        mActivityTabTabObserver = new ActivityTabTabObserver(activityTabProvider) {
            @Override
            protected void onObservingDifferentTab(Tab tab) {
                if (mButtonStateListener != null) {
                    Boolean isEnabled = mButtonStateListener.onObservingDifferentTab(tab);
                    setEnabled(isEnabled);
                    mImageButton.setEnabled(isEnabled);
                }
            }

            @Override
            public void onUpdateUrl(Tab tab, String url) {
                if (mButtonStateListener != null) {
                    Boolean isEnabled = mButtonStateListener.onUpdateUrl(tab, url);
                    setEnabled(isEnabled);
                    mImageButton.setEnabled(isEnabled);
                }
            }
        };
    }

    public void setButtonState(ButtonState mButtonState) {
        this.mButtonStateListener = mButtonState;
    }

    @Override
    public void onTintChanged(ColorStateList tint, boolean useLight) {
        ApiCompatibilityUtils.setImageTintList(mImageButton, tint);
    }

    public void destroy() {
        if (mThemeColorProvider != null) {
            mThemeColorProvider.removeTintObserver(this);
            mThemeColorProvider = null;
        }

        if (mActivityTabTabObserver != null) {
            mActivityTabTabObserver.destroy();
            mActivityTabTabObserver = null;
        }
    }

    interface ButtonState {
        Boolean onObservingDifferentTab(Tab tab);
        Boolean onUpdateUrl(Tab tab, String url);
    }
}