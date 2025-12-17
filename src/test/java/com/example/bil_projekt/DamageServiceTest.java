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
    private DamageRepository damageRepo;

    // Mockito injicerer mock i @Autowired-feltet
    @InjectMocks
    private DamageService damageService;

    @Test
    void happyFlow_addDamages_validDamage_createsDamage() {

        // Arrange
        DamageReport damage = new DamageReport(
                0,
                10,
                "Ridse på dør",
                "Medium",
                1500.0,
                "Lakskade"
        );

        // Act
        damageService.addDamages(10, List.of(damage));

        // Assert
        verify(damageRepo).create(damage);
    }

    @Test
    void happyFlow_addDamages_multipleDamages_allCreated() {

        // Arrange
        DamageReport d1 = new DamageReport(
                0,
                10,
                "Ridse",
                "Lav",
                500.0,
                "Lakskade"
        );

        DamageReport d2 = new DamageReport(
                0,
                10,
                "Revnet forrude",
                "Høj",
                3000.0,
                "Forrude"
        );

        // Act
        damageService.addDamages(10, List.of(d1, d2));

        // Assert
        verify(damageRepo).create(d1);
        verify(damageRepo).create(d2);
        verify(damageRepo, times(2)).create(any(DamageReport.class));
    }

    @Test
    void exceptionFlow_addDamages_negativePrice_throwsException() {

        // Arrange
        DamageReport bad = new DamageReport(
                0,
                10,
                "Ugyldig skade",
                "Medium",
                -1.0,
                "Lakskade"
        );

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                () -> damageService.addDamages(10, List.of(bad))
        );

        // Repository må ikke kaldes ved exception
        verify(damageRepo, never()).create(any());
    }
}
