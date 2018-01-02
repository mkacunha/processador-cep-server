package com.mkacunha.processadorcep.application.controller.cepfile;

import com.mkacunha.processadorcep.domain.cep.cepfile.CepFile;
import com.mkacunha.processadorcep.infrastructure.exception.CepFileTranslatorException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CepFileTranslatorTest {

	@Rule
	public ExpectedException exception = none();

	@InjectMocks
	private CepFileTranslator cepFileTranslator;

	@Mock
	private MultipartFile file;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void deve_retornar_arquivo_obrigatorio() {
		exception.expect(CepFileTranslatorException.class);
		exception.expectMessage(CepFileTranslator.MSG_ARQUIVO_OBRIGATORIO);
		cepFileTranslator.apply(null);
	}

	@Test
	public void deve_retornar_extensao_incorreta(){
		when(file.getOriginalFilename()).thenReturn("file.hmlt");
		exception.expect(CepFileTranslatorException.class);
		exception.expectMessage(CepFileTranslator.MSG_EXTENSAO_INCORRETA);
		cepFileTranslator.apply(file);
	}

	@Test
	public void deve_retornar_arquivo_vazio() throws IOException {
		when(file.getOriginalFilename()).thenReturn("file.txt");
		when(file.getBytes()).thenReturn(new String().getBytes());

		exception.expect(CepFileTranslatorException.class);
		exception.expectMessage(CepFileTranslator.MSG_ARQUIVO_VAZIO);
		cepFileTranslator.apply(file);
	}

	@Test
	public void deve_traduzir_arquivo() throws IOException {
		when(file.getOriginalFilename()).thenReturn("file.txt");
		when(file.getBytes()).thenReturn(new String("87260000;87005270").getBytes());
		CepFile translate = cepFileTranslator.apply(file);

		assertThat(translate.getName()).isEqualTo("file.txt");
		assertThat(translate.getCeps().size()).isEqualTo(2);
		assertThat(translate.getCeps().contains("87260000")).isTrue();
		assertThat(translate.getCeps().contains("87005270")).isTrue();
	}


}