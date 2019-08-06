// Copyright 2019 The Triple Banana Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.triple.banana;

import org.triple.banana.public_api.export.BananaApplication;
import org.triple.banana.public_api.export.BananaCommandLine;
import org.triple.banana.public_api.export.BananaHooks;
import org.triple.banana.public_api.export.BananaTab;

public class TripleBananaApplication extends BananaApplication {
    @Override
    public boolean onInitializeHooks(BananaHooks hooks) {
        hooks.setEventListener(new BananaHooks.Listener() {
            @Override
            public void initCommandLine(BananaCommandLine commandLine) {
                commandLine.appendSwitchWithValue(
                        "enable-features", "ChromeDuet,HomePageButtonForceEnabled");
            }

            @Override
            public void onUrlUpdated(BananaTab tab) {
                MediaSuspendController.instance.DisableOnYouTube(tab);
            }
        });

        return true;
    }
}
