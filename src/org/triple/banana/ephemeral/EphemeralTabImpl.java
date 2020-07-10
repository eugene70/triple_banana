// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.ephemeral;

import org.banana.cake.interfaces.BananaApplicationUtils;
import org.triple.banana.ephemeral.mojom.EphemeralTab;

import org.chromium.mojo.system.MojoException;
import org.chromium.services.service_manager.InterfaceFactory;

import java.io.File;

public class EphemeralTabImpl implements EphemeralTab {
    @Override
    public void open(final String url) {
        BananaApplicationUtils.get().showInfoPage(url);
    }

    @Override
    public void close() {}

    @Override
    public void onConnectionError(MojoException e) {}

    public static class Factory implements InterfaceFactory<EphemeralTab> {
        public Factory() {}

        @Override
        public EphemeralTab createImpl() {
            return new EphemeralTabImpl();
        }
    }
}
