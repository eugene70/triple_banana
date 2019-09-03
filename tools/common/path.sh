#!/bin/bash
#
# Copyright 2019 The Triple Banana Authors. All rights reserved.
# Use of this source code is governed by a BSD-style license that can be
# found in the LICENSE file.

function triple_banana_tools_path() {
  echo $TOOLS_PATH
}

function triple_banana_path() {
  echo $(realpath $(triple_banana_tools_path)/..)
}

function triple_banana_command_path() {
  echo $(triple_banana_tools_path)/command
}

function triple_banana_upstream_path() {
  echo $(triple_banana_path)/upstream
}

function chromium_path() {
  echo $(realpath $(triple_banana_path)/..)
}
