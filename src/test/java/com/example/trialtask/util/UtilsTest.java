package com.example.trialtask.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UtilsTest {
    @Test
    void anyStringEquals_true() {
        String str = "Bear";
        String[] strs = {"Cat", "Bear", "Mouse"};

        assertTrue(Utils.anyStringEquals(str, strs));
    }

    @Test
    void anyStringEquals_false() {
        String str = "Moose";
        String[] strs = {"Horse", "Dog", "Pig"};

        assertFalse(Utils.anyStringEquals(str, strs));
    }
}
