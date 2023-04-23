package ru.hunt.Request.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.hunt.Request.model.Status;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.name=application-test")
public class StatusRepositoryTest {

    @Autowired
    private StatusRepository statusRepository;

    @Test
    public void getByName_ShouldReturnObjectFromDB() {
        var status = new Status();
        var statusName = "на рассмотрении";
        status.setId(0L);
        status.setName(statusName);
        statusRepository.save(status);

        var found = statusRepository.getByName(statusName);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(statusName);
    }
}
