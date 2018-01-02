package com.mkacunha.processadorcep.application.controller.cepfile;

import com.mkacunha.processadorcep.domain.cep.cepfile.CepFile;
import com.mkacunha.processadorcep.infrastructure.exception.CepFileTranslatorException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class CepFileTranslator implements Function<MultipartFile, CepFile> {

	public static final String MSG_ARQUIVO_OBRIGATORIO = "Deve ser informado um arquivo para processamento.";

	public static final String MSG_EXTENSAO_INCORRETA = "Extensão do arquivo informado não implementada. Deve ser infformado um arquivo de  extensão .txt.";

	public static final String EXTENSAO_TXT = ".txt";

	public static final String MSG_ERRO_AO_LER_ARQUIVO = "Erro ao ler arquivo de ceps. Verifique se o arquivo não está em branco e se existe apenas CEPs válidos separados por vírgula.";

	public static final String MSG_ARQUIVO_VAZIO = "Arquivo informado está vazio, não possui CEPs a serem processados.";

	@Override
	public CepFile apply(MultipartFile file) {
		validateRequeredFile(file);
		validateExtensaoFile(file);
		CepFile cepFile = toCepFile(file);
		validateCeps(cepFile);
		return cepFile;
	}

	private void validateCeps(CepFile cepFile) {
		if (cepFile.getCeps().isEmpty()) {
			throw new CepFileTranslatorException(MSG_ARQUIVO_VAZIO);
		}
	}

	private CepFile toCepFile(MultipartFile file) {
		try {
			String[] strings = new String(file.getBytes()).split(";");
			List<String> ceps = Arrays.asList(strings).stream().filter(cep -> !cep.trim().isEmpty())
									  .collect(Collectors.toList());
			return new CepFile(file.getOriginalFilename(), ceps);
		} catch (IOException e) {
			throw new CepFileTranslatorException(MSG_ERRO_AO_LER_ARQUIVO);
		}
	}

	private void validateRequeredFile(MultipartFile file) {
		if (isNull(file)) {
			throw new CepFileTranslatorException(MSG_ARQUIVO_OBRIGATORIO);
		}
	}

	private void validateExtensaoFile(MultipartFile file) {
		if (!file.getOriginalFilename().endsWith(EXTENSAO_TXT)) {
			throw new CepFileTranslatorException(MSG_EXTENSAO_INCORRETA);
		}
	}
}
