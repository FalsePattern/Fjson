package com.falsepattern.json.schema;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.node.ObjectNode;
import lombok.val;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Draft4 {

    private static final List<String> ORDER = Arrays.asList("items", "additionalItems", "properties", "patternProperties", "additionalProperties");

    /**
     * Generates a rule that validates a JSON object against a JSON schema.
     * @param schema The JSON schema to build the rule from.
     * @return The rule that validates a JSON object against the JSON schema.
     */
    static Rule convert(JsonNode schema) {
        if (schema.isBoolean()) {
            return (json) -> schema.boolValue();
        } else if (schema.isObject()) {
            return (jsonn) -> schema.getJavaMap().entrySet().stream().sorted(Comparator.comparingInt((entry) -> ORDER.indexOf(entry.getKey()))).map((entry) -> {
                boolean hadItems = false;
                val key = entry.getKey();
                val subSchema = entry.getValue();
                switch (key) {
                    case "enum":
                        return convertEnum(subSchema);
                    case "items":
                        return convertItems(subSchema);
                    case "additionalItems":
                        if (!hadItems) {
                            hadItems = true;
                            val itemSchema = convertItems(new ObjectNode());
                            return (Rule) (json) -> itemSchema.validate(json) && convertAdditionalItems(subSchema).validate(json);
                        }
                        return convertAdditionalItems(subSchema);
                    case "type":
                        return convertType(subSchema);
                    case "properties":
                        return convertProperties(subSchema);
                    case "patternProperties":
                        return convertPatternProperties(subSchema);
                    case "additionalProperties":
                        return convertAdditionalProperties(subSchema);
                    case "minimum":
                        return convertMinimum(subSchema, schema.getBoolOrDefault("exclusiveMinimum", false));
                    case "maximum":
                        return convertMaximum(subSchema, schema.getBoolOrDefault("exclusiveMaximum", false));
                    case "minItems":
                        return convertMinItems(subSchema);
                    case "minLength":
                        return convertMinLength(subSchema);
                    case "maxLength":
                        return convertMaxLength(subSchema);
                    case "maxItems":
                        return convertMaxItems(subSchema);
                    case "multipleOf":
                        return convertMultipleOf(subSchema);
                    case "required":
                        return convertRequired(subSchema);
                    case "allOf":
                        return convertAllOf(subSchema);
                    case "anyOf":
                        return convertAnyOf(subSchema);
                    case "oneOf":
                        return convertOneOf(subSchema);
                    case "not":
                        return convertNot(subSchema);
                    default: return null;
                }
            }).filter(Objects::nonNull).allMatch((rule) -> rule.validate(jsonn));
        } else {
            return (json) -> false;
        }
    }

    private static Rule convertAdditionalItems(JsonNode schema) {
        if (schema.isBoolean()) {
            return schema.boolValue() ? (json) -> true : (json) -> !json.isList() || json.getJavaList().stream().allMatch(JsonNode::isValidated);
        } else {
            val rule = convert(schema);
            return (json) -> json.isList() && json.getJavaList().stream().filter((node) -> !node.isValidated()).allMatch(rule::validate);
        }
    }

    private static Rule convertItems(JsonNode schema) {
        if (schema.isList()) {
            val rules = schema.getJavaList().stream().map(Draft4::convert).collect(Collectors.toList());
            val ruleCount = rules.size();
            return (json) -> {
                if (!json.isList()) return true;
                val count = Math.min(ruleCount, json.size());
                boolean allValid = true;
                for (int i = 0; i < count; i++) {
                    val element = json.get(i);
                    if (!rules.get(i).validate(element)) {
                        allValid = false;
                    }
                    element.setValidationStatus(ValidationStatus.VALIDATED);
                }
                return allValid;
            };
        } else {
            val rule = convert(schema);
            return (json) -> !json.isList() || json.getJavaList().stream().allMatch((node) -> {
                node.setValidationStatus(ValidationStatus.VALIDATED);
                return rule.validate(node);
            });
        }
    }

    private static Rule convertMultipleOf(JsonNode schema) {
        return (json) -> !json.isNumber() || json.bigDecimalValue().remainder(schema.bigDecimalValue()).compareTo(BigDecimal.ZERO) == 0;
    }

    private static Rule convertMinLength(JsonNode schema) {
        return (json) -> !json.isString() || BigInteger.valueOf(json.stringValue().length()).compareTo(schema.bigIntValue()) >= 0;
    }


    private static Rule convertMaxLength(JsonNode schema) {
        return (json) -> !json.isString() || BigInteger.valueOf(json.stringValue().length()).compareTo(schema.bigIntValue()) <= 0;
    }

    private static Rule convertNot(JsonNode schema) {
        return (json) -> !convert(schema).validate(json);
    }

    private static Rule convertRequired(JsonNode schema) {
        return (json) -> !json.isObject() || schema.getJavaList().stream().allMatch((key) -> json.containsKey(key.stringValue()));
    }

    private static Rule convertMaxItems(JsonNode schema) {
        return (json) -> !json.isList() || BigDecimal.valueOf(json.size()).compareTo(schema.bigDecimalValue()) <= 0;
    }

    private static Rule convertEnum(JsonNode schema) {
        return (json) -> schema.getJavaList().stream().anyMatch((enumJson) -> enumJson.equals(json));
    }

    private static Rule convertMinItems(JsonNode schema) {
        return (json) -> !json.isList() || BigDecimal.valueOf(json.size()).compareTo(schema.bigDecimalValue()) >= 0;
    }

    private static List<Rule> genList(JsonNode schema) {
        return schema.getJavaList().stream().map(Draft4::convert).collect(Collectors.toList());
    }
    private static Rule convertAllOf(JsonNode schema) {
        val rules = genList(schema);
        return (json) -> rules.stream().allMatch((rule) -> rule.validate(json));
    }

    private static Rule convertAnyOf(JsonNode schema) {
        val rules = genList(schema);
        return (json) -> rules.stream().anyMatch((rule) -> rule.validate(json));
    }


    private static Rule convertOneOf(JsonNode schema) {
        val rules = genList(schema);
        return (json) -> rules.stream().map((rule) -> rule.validate(json) ? 1 : 0).reduce(0, Integer::sum) == 1;
    }

    private static Rule convertMinimum(JsonNode schema, boolean exclusive) {
        return (json) -> {
            if (!json.isNumber()) return true;
            val result = json.bigDecimalValue().compareTo(schema.bigDecimalValue());
            return exclusive ? result > 0 : result >= 0;
        };
    }

    private static Rule convertMaximum(JsonNode schema, boolean exclusive) {
        return (json) -> {
            if (!json.isNumber()) return true;
            val result = json.bigDecimalValue().compareTo(schema.bigDecimalValue());
            return exclusive ? result < 0 : result <= 0;
        };
    }

    private static Rule convertPatternProperties(JsonNode schema) {
        val rules = new HashMap<Function<String, Boolean>, Rule>();
        schema.getJavaMap().forEach((key, value) -> rules.put((str) -> Pattern.compile(key).matcher(str).find(), convert(value)));
        return validatePropertiesPopping(rules);
    }

    private static Rule convertAdditionalProperties(JsonNode schema) {
        if (schema.isBoolean()) {
            return (json) -> !json.isObject() || schema.boolValue() || json.size() == 0;
        } else {
            val rule = convert(schema);
            return (json) -> !json.isObject() || json.getJavaMap().values().stream().filter((node) -> node.getValidationStatus() == ValidationStatus.UNVALIDATED).allMatch(rule::validate);
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
                val entry = json.get(key);
                if (matchingFilters.stream().allMatch((rule) -> rule.validate(entry))) {
                    entry.setValidationStatus(ValidationStatus.VALIDATED);
                    return true;
                }
                entry.setValidationStatus(ValidationStatus.UNVALIDATED);
                return false;
            });
        };
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
}
