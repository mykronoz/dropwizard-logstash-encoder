package com.wikia.dropwizard.logstash.appender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.fieldnames.LogstashFieldNames;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class LogstashAppenderFactoryHelper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static LogstashFieldNames getFieldNamesFromHashMap(HashMap<String, String> map) {
    LogstashFieldNames fieldNames = new LogstashFieldNames();

    fieldNames.setTimestamp(map.getOrDefault("timestamp", "@timestamp"));
    fieldNames.setVersion(map.getOrDefault("version", "@version"));
    fieldNames.setMessage(map.getOrDefault("message", "message"));
    fieldNames.setLogger(map.getOrDefault("logger", "logger_name"));
    fieldNames.setThread(map.getOrDefault("thread", "thread_name"));
    fieldNames.setLevel(map.getOrDefault("level", "level"));
    fieldNames.setLevelValue(map.getOrDefault("levelValue", "level_value"));
    fieldNames.setCaller(map.getOrDefault("caller", null));
    fieldNames.setCallerClass(map.getOrDefault("callerClass", "caller_class_name"));
    fieldNames.setCallerMethod(map.getOrDefault("callerMethod", "caller_method_name"));
    fieldNames.setCallerFile(map.getOrDefault("callerFile", "caller_file_name"));
    fieldNames.setCallerLine(map.getOrDefault("callerLine", "caller_line_number"));
    fieldNames.setStackTrace(map.getOrDefault("stackTrace", "stack_trace"));
    fieldNames.setTags(map.getOrDefault("tags", "tags"));
    fieldNames.setMdc(map.getOrDefault("mdc", null));
    fieldNames.setContext(map.getOrDefault("context", null));

    return fieldNames;
  }

  public static String getCustomFieldsFromHashMap(HashMap<String, String> map, String applicationName) {
    try {
      map.putIfAbsent("applicationName", applicationName);
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return "{}";
    }
  }
}
