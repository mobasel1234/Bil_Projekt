package com.example.bil_projekt;

import com.example.bil_projekt.model.DamageReport;
import com.example.bil_projekt.repository.DamageRepository;
import com.example.bil_projekt.service.DamageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DamageServiceTest {

    @Mock
    private DamageRepository repo;

    // Mockito injicerer mock i @Autowired-feltet
    @InjectMocks
    private DamageService service;

    @Test
    void exceptionFlow_addDamage_negativePrice() {

        DamageReport bad = new DamageReport(
                0,
                10,
                "Test",
                "Medium",
                -1.0,
                "Lakskade"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addDamages(10, List.of(bad))
        );

        verify(repo, never()).create(any());
    }
}
