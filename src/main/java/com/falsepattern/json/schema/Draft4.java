package com.falsepattern.json.schema;

import com.falsepattern.json.node.JsonNode;
import lombok.val;
import org.jetbrains.annotations.Contract;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Draft4 {

    private static final List<String> ORDER = Arrays.asList("properties", "additionalProperties", "patternProperties");

    /**
     * Generates a rule that validates a JSON object against a JSON schema. This method creates a wrapper rule that
     * automatically clones the JSON object before validating it, so that the original JSON object is not modified.
     * @param schema The JSON schema to build the rule from.
     * @return The rule that validates a JSON object against the JSON schema.
     */
    @Contract(pure = true)
    public static Rule convert(JsonNode schema) {
        return (json) -> convertI(schema).validate(json.clone());
    }

    private static Rule convertI(JsonNode schema) {
        if (schema.isBoolean()) {
            return convertBoolean(schema);
        } else if (schema.isObject()) {
            return (json) -> schema.getJavaMap().entrySet().stream().sorted(Comparator.comparingInt((entry) -> ORDER.indexOf(entry.getKey()))).map((entry) -> {
                val key = entry.getKey();
                val value = entry.getValue();
                switch (key) {
                    case "enum":
                        return convertEnum(value);
                    case "type":
                        return convertType(value);
                    case "properties":
                        return convertProperties(value);
                    case "patternProperties":
                        return convertPatternProperties(value);
                    case "additionalProperties":
                        return convertAdditionalProperties(value);
                    case "description": return (Rule) (json2) -> true;
                    default:
                        System.err.println("Unknown schema key \"" + key + "\"");
                        return (Rule) (json2) -> false;
                }
            }).allMatch((rule) -> rule.validate(json));
        } else {
            return (json) -> false;
        }
    }

    private static Rule convertPatternProperties(JsonNode schema) {
        val rules = new HashMap<Function<String, Boolean>, Rule>();
        schema.getJavaMap().forEach((key, value) -> rules.put((str) -> Pattern.compile(key).matcher(str).find(), convert(value)));
        return (json) -> !json.isObject() ||
                         json.getJavaMap()
                             .entrySet()
                             .stream()
                             .allMatch((entry) -> {
                                val key = entry.getKey();
                                val data = entry.getValue();
                                val matchers = rules.entrySet()
                                        .stream()
                                        .filter((ruleEntry) -> ruleEntry.getKey().matcher(key).find())
                                        .collect(Collectors.toSet());
                                if (matchers.size() == 0) return true;
                                if (matchers.stream().allMatch((ruleEntry) -> {
                                    val rule = ruleEntry.getValue();
                                    return rule.validate(data.clone());
                                })) {
                                    json.remove(key);
                                    return true;
                                }
                                return false;
                             });
    }

    private static Rule convertAdditionalProperties(JsonNode schema) {
        if (schema.isBoolean()) {
            return (json) -> !json.isObject() || schema.boolValue() || json.size() == 0;
        } else {
            return (json) -> false;
        }
    }

    private static Rule convertProperties(JsonNode schema) {
        val rules = new HashMap<Function<String, Boolean>, Rule>();
        schema.getJavaMap().forEach((key, value) -> rules.put(key::equals, convert(value)));
        return validatePropertiesPopping(rules);
    }

    private static Rule validatePropertiesPopping(Map<Function<String, Boolean>, Rule> filter) {
        return (json) -> {
            if (!json.isObject()) return true;
            val keys = json.getJavaMap().keySet().toArray(new String[0]);
            return Arrays.stream(keys).allMatch((key) -> {
                val matchingFilters = filter.entrySet().stream().filter((entry) -> entry.getKey().apply(key)).map(Map.Entry::getValue).collect(Collectors.toList());
                if (matchingFilters.size() == 0) return true;
                if (matchingFilters.stream().allMatch((rule) -> rule.validate(json.get(key).clone()))) {
                    json.remove(key);
                    return true;
                }
                return false;
            });
        };
    }

    private static Rule convertEnum(JsonNode schema) {
        return (json) -> schema.getJavaList().stream().anyMatch((enumJson) -> enumJson.equals(json));
    }

    private static Rule convertType(JsonNode schema) {
        if (schema.isString()) {
            switch (schema.stringValue()) {
                case "integer":
                    return JsonNode::isInt;
                case "number":
                    return JsonNode::isNumber;
                case "string":
                    return JsonNode::isString;
                case "object":
                    return JsonNode::isObject;
                case "array":
                    return JsonNode::isList;
                case "boolean":
                    return JsonNode::isBoolean;
                case "null":
                    return JsonNode::isNull;
                default:
                    throw new IllegalArgumentException("Unknown type " + schema.stringValue());
            }
        } else {
            return (json) -> schema.getJavaList().stream().map(Draft4::convertType).anyMatch((rule) -> rule.validate(json));
        }
    }


    private static Rule convertBoolean(JsonNode schema) {
        return (json) -> schema.boolValue();
    }
}
