package com.falsepattern.json;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class Util {
    @NotNull
    @Contract("null, _ -> param2; !null, _ -> param1")
    public static <T> T orElse(@Nullable T thing, @NotNull T defaultThing) {
        return thing == null ? defaultThing : thing;
    }

    @NotNull
    @SneakyThrows
    @Contract("null, _ -> fail; !null, _ -> param1")
    public static <T> T orElseThrow(@Nullable T thing, @NotNull Supplier<Exception> exceptionSupplier) {
        if (thing == null) {
            throw exceptionSupplier.get();
        }
        return thing;
    }
}
