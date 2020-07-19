// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

#include "triple_banana/modules/video/video_manager_android.h"

#include "base/android/jni_android.h"
#include "triple_banana/modules/jni_headers/VideoManager_jni.h"

using base::android::AttachCurrentThread;

namespace triple_banana {

VideoManagerAndroid::VideoManagerAndroid() {
  jobject_.Reset(modules::Java_VideoManager_create(
      AttachCurrentThread(), reinterpret_cast<intptr_t>(this)));
}

VideoManagerAndroid::~VideoManagerAndroid() = default;

void VideoManagerAndroid::showVideo() {
    JNIEnv* env = AttachCurrentThread();
    modules::Java_VideoManager_showVideo(env, jobject_);
}
}  // namespace triple_banana
