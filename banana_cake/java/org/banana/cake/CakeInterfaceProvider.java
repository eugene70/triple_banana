// Copyright 2020 The Triple Banana Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.banana.cake;

import org.banana.cake.interfaces.BananaInterfaceProvider;

public class CakeInterfaceProvider {
    public static void initialize() {
        BananaInterfaceProvider.register(org.banana.cake.interfaces.BananaCommandLine.class,
                org.banana.cake.CakeCommandLine::new);
        BananaInterfaceProvider.register(
                org.banana.cake.interfaces.BananaTab.class, org.banana.cake.CakeTab::new);
        BananaInterfaceProvider.register(org.banana.cake.interfaces.BananaTabManager.class,
                org.banana.cake.CakeTabManager::new);
    }
}
