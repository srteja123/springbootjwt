package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.builder.SpringApplicationBuilder;

@ExtendWith(MockitoExtension.class)
public class ServletInitializerTest {
    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    @Test
    public void testServletInitializer() {
        ServletInitializer servletInitializer = new ServletInitializer();
        when(springApplicationBuilder.sources(DemoApplication.class)).thenReturn(springApplicationBuilder);
        SpringApplicationBuilder result = servletInitializer.configure(springApplicationBuilder);
        verify(springApplicationBuilder).sources(DemoApplication.class);
        assertEquals(springApplicationBuilder, result);
    }
}
