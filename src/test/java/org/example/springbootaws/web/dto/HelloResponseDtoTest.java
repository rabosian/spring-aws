package org.example.springbootaws.web.dto;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class HelloResponseDtoTest {
    @Test
    public void testLombok() {
        // dummy data
        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);

        assertThat(dto.getName()).isEqualTo(name);
        assertThat (dto.getAmount()).isEqualTo(amount);
    }

}
