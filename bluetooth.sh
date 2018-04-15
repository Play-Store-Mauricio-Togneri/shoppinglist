#!/usr/bin/env bash

$ANDROID_HOME/platform-tools/adb forward tcp:4444 localabstract:/adb-hub
$ANDROID_HOME/platform-tools/adb connect 127.0.0.1:4444