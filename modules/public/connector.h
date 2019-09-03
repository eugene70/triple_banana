// Copyright 2019 The Triple Banana Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

#ifndef TRIPLE_BANANA_MODULES_PUBLIC_CONNECTOR_H_
#define TRIPLE_BANANA_MODULES_PUBLIC_CONNECTOR_H_

#include "content/child/child_process.h"
#include "content/child/child_thread_impl.h"
#include "content/public/browser/system_connector.h"
#include "mojo/public/cpp/bindings/interface_request.h"
#include "triple_banana/modules/public/mojom/authentication.mojom.h"
#include "triple_banana/modules/public/mojom/hello.mojom.h"

namespace triple_banana {

template <typename Interface>
inline mojo::InterfacePtr<Interface> BindInterfaceOnRenderer() {
  mojo::InterfacePtr<Interface> interface_ptr = nullptr;
  content::ChildThreadImpl::current()->GetConnector()->BindInterface(
      "triple_banana", mojo::MakeRequest(&interface_ptr));
  return interface_ptr;
}

template <typename Interface>
inline mojo::InterfacePtr<Interface> BindInterfaceOnBrowser() {
  mojo::InterfacePtr<Interface> interface_ptr = nullptr;
  content::GetSystemConnector()->BindInterface(
      "triple_banana", mojo::MakeRequest(&interface_ptr));
  return interface_ptr;
}

}  // namespace triple_banana

#endif  // TRIPLE_BANANA_MODULES_PUBLIC_CONNECTOR_H_