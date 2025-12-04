package com.example.bil_projekt;

import com.example.bil_projekt.Controller.CarController;
import com.example.bil_projekt.CarBuisness.Car;
import com.example.bil_projekt.Repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;




    public class CarControllerTest {

        // ----------- HAPPY FLOW -----------
        @Test
        void testShowCarStatus_HappyFlow() {

            // Mock repository
            CarRepository mockRepo = Mockito.mock(CarRepository.class);

            // Fake bil-liste
            List<Car> cars = new ArrayList<>();
            Car c1 = new Car();
            c1.setCar_id(1);
            c1.setStatus("Ledig");
            cars.add(c1);

            Mockito.when(mockRepo.findAll()).thenReturn(cars);

            // Mock Model
            Model mockModel = Mockito.mock(Model.class);

            // Controller
            CarController controller = new CarController();
            controller.setCarRepository(mockRepo);  // <-- du får dette sætteren nedenfor

            // Kald metoden
            String view = controller.showCarStatus(mockModel);

            // Assertions
            assertEquals("carStatus", view);
            Mockito.verify(mockModel).addAttribute("cars", cars);
        }


        // ----------- EXCEPTION FLOW -----------
        @Test
        void testShowCarStatus_ExceptionFlow() {

            CarRepository mockRepo = Mockito.mock(CarRepository.class);

            // Repository fejler
            Mockito.when(mockRepo.findAll())
                    .thenThrow(new RuntimeException("DB error"));

            Model mockModel = Mockito.mock(Model.class);

            CarController controller = new CarController();
            controller.setCarRepository(mockRepo);

            assertThrows(RuntimeException.class, () -> controller.showCarStatus(mockModel));
        }
    }


