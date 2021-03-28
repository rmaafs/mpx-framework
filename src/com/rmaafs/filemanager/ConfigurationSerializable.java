package com.rmaafs.filemanager;

import java.util.Map;

public interface ConfigurationSerializable {
    Map<String, Object> serialize();
}