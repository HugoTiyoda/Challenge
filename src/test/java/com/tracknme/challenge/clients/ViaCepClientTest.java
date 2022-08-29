package com.tracknme.challenge.clients;

import com.tracknme.challenge.domain.ViaCepDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

class ViaCepClientTest {
    @InjectMocks
    private ViaCepClient viaCepClient;

    @Test
    void shouldFindAddressbyCep() {
        ViaCepClient client = new ViaCepClient();
        ViaCepDTO cep = client.findCep("01536000");
        assertThat(cep).hasNoNullFieldsOrProperties();
    }

}