LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

src_dirs := src

LOCAL_SRC_FILES := $(call all-java-files-under, $(src_dirs))

WITH_DEXPREOPT := false

#LOCAL_AIDL_INCLUDES := android/os/Person.aidl

LOCAL_PACKAGE_NAME := hello
LOCAL_CERTIFICATE := shared

include $(BUILD_PACKAGE)

include $(call all-makefiles-under, $(LOCAL_PATH))