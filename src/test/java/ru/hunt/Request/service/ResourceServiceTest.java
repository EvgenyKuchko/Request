package ru.hunt.Request.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hunt.Request.model.Resource;
import ru.hunt.Request.repository.ResourceRepository;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    private static Resource resource;
    private static final int AMOUNT = 5;
    private static final long ID = 0L;

    @Before
    public void setUp() {
        resource = new Resource();
        resource.setAmount(10);
        resource.setId(ID);
    }

    @Test
    public void changeAmount_shouldChangeAmountOfResource() {
        doNothing().when(resourceRepository).changeAmount(AMOUNT, ID);

        resourceService.changeAmount(resource, AMOUNT);

        verify(resourceRepository, times(1)).changeAmount(AMOUNT, ID);
    }
}