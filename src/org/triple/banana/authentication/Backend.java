// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

package org.triple.banana.authentication;

import org.triple.banana.authentication.Authenticator.Callback;

public interface Backend {
    void authenticate(Callback callback);
    void authenticate(boolean isBackground, Callback callback);
}
