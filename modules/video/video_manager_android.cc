// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

#include "triple_banana/modules/video/video_manager_android.h"

#include "base/android/jni_android.h"
#include "base/optional.h"
#include "content/common/media/media_player_delegate_messages.h"
#include "content/public/browser/media_player_id.h"
#include "content/public/browser/render_frame_host.h"
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

void VideoManagerAndroid::stop(content::MediaPlayerId* fullscreen_player) {
  LOG(INFO) << "HELLO STOP!";
  if (!fullscreen_player) {
    LOG(INFO) << "HELLO !fullscreen_player";
    fullscreen_player->render_frame_host->Send(new MediaPlayerDelegateMsg_Pause(
        fullscreen_player->render_frame_host->GetRoutingID(),
        fullscreen_player->delegate_id, true));
  }
}
}  // namespace triple_banana
