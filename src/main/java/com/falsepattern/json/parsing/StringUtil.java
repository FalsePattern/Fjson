package com.falsepattern.json.parsing;

import lombok.val;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtil {
    public static String indent(String input, int indent) {
        return Arrays.stream(input.split("\n")).map((line) -> {
            val result = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                result.append(' ');
            }
            return result.append(line).append('\n');
        }).collect(Collectors.joining());
    }
}
