// Copyright 2020 The Triple Banana Authors. All rights reserved.
// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at https://mozilla.org/MPL/2.0/.

#ifndef TRIPLE_BANANA_MODULES_VIDEO_MANAGER_ANDROID_H
#define TRIPLE_BANANA_MODULES_VIDEO_MANAGER_ANDROID_H

#include <memory>
#include "base/android/jni_android.h"
#include "base/macros.h"
#include "base/optional.h"
#include "content/public/browser/media_player_id.h"

namespace triple_banana {

class VideoManagerAndroid {
 public:
  explicit VideoManagerAndroid();
  virtual ~VideoManagerAndroid();
  void showVideo();
  void stop(content::MediaPlayerId* fullscreen_player);

 private:
  base::android::ScopedJavaGlobalRef<jobject> jobject_;

  DISALLOW_COPY_AND_ASSIGN(VideoManagerAndroid);
};

}  // namespace triple_banana

#endif  // TRIPLE_BANANA_MODULES_VIDEO_MANAGER_ANDROID_H 
