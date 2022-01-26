package com.falsepattern.json.test;

import com.falsepattern.json.node.JsonNode;
import com.falsepattern.json.parsing.InvalidSyntaxException;
import com.falsepattern.json.schema.Rule;
import com.falsepattern.json.schema.subrules.Schema;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonTest {
    public String description;
    public Rule schema;
    public List<Case> cases = new ArrayList<>();
    public JsonTest(JsonNode json) {
        description = json.getStringOrDefault("description", "");
        schema = Schema.parse(json.get("schema"));
        val caseList = json.get("tests");
        val caseCount = caseList.size();
        for (int i = 0; i < caseCount; i++) {
            cases.add(new Case(caseList.get(i)));
        }
    }

    public Stream<DynamicNode> testCases() {
        return cases.stream().map((testCase) -> DynamicTest.dynamicTest(testCase.description, () -> testCase.validateAgainst(schema)));
    }

    @SneakyThrows
    private static JsonNode loadFromFile(Path path) {
        val file = String.join("\n", Files.readAllLines(path));
        try {
            return JsonNode.parse(file);
        } catch (InvalidSyntaxException e) {
            throw new RuntimeException(file, e);
        }
    }

    public static Stream<DynamicNode> testFile(File file) {
        val json = loadFromFile(file.toPath());
        return json.getJavaList().stream().map((entry) -> DynamicContainer.dynamicContainer(entry.getStringOrDefault("description", "No Description"), new JsonTest(entry).testCases()));
    }
}
