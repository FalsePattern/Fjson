package com.falsepattern.json.schema;

import com.falsepattern.json.test.JsonTest;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class TestDraft04 {
    @TestFactory
    public Stream<DynamicNode> testAllOfThem() {
        return Arrays.stream(Objects.requireNonNull(Paths.get(".", "JSON-Schema-Test-Suite", "tests", "draft4").toFile().listFiles()))
                .filter((file) -> file.getName().endsWith(".json"))
                .sorted(Comparator.comparing(File::getName))
                .map((file) -> DynamicContainer.dynamicContainer(file.getName(), file.toURI(), JsonTest.testFile(file, Draft4::convert)));
    }
}
