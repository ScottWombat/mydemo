package org.demo.test.unit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoTest {

    @Test
    public void testNow(){
        log.info("testNow");
        assertTrue(true);
    }
    
}
