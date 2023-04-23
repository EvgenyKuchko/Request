package ru.hunt.Request.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hunt.Request.model.Status;
import ru.hunt.Request.repository.StatusRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class StatusServiceTest {

    @InjectMocks
    private StatusService statusService;

    @Mock
    private StatusRepository statusRepository;

    private static final String APPROVED_STATUS = "одобрено";
    private static Status approved;

    @Before
    public void setUp() {
        approved = new Status(0L, APPROVED_STATUS);
    }

    @Test
    public void getStatusByName_ShouldReturnApproved() {

        when(statusRepository.getByName(APPROVED_STATUS)).thenReturn(approved);
        Status expected = statusService.getStatusByName(APPROVED_STATUS);

        assertThat(expected).isNotNull();
        assertThat(approved.getName()).isEqualTo(expected.getName());
    }
}