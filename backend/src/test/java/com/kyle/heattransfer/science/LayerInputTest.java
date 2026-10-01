package com.kyle.heattransfer.science;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LayerInputTest {

    @Test
    void rejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new LayerInput(" ", 0.2, 0.72));
    }

    @Test
    void rejectsZeroThickness() {
        assertThrows(IllegalArgumentException.class,
                () -> new LayerInput("Brick", 0.0, 0.72));
    }

    @Test
    void rejectsZeroConductivity() {
        assertThrows(IllegalArgumentException.class,
                () -> new LayerInput("Brick", 0.2, 0.0));
    }
}