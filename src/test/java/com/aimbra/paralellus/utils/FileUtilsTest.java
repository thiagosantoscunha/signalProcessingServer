package com.aimbra.paralellus.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    private String base64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAP";

    @Test
    void getImageString() {
        String actual = "iVBORw0KGgoAAAANSUhEUgAAAP";
        String expected = FileUtils.getImageString(this.base64);
        Assertions.assertEquals(expected, actual);
    }
}
