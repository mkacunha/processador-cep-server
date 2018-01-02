package com.mkacunha.processadorcep.application.controller.historico;

import com.mkacunha.processadorcep.domain.historico.Historico;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HistoricoTranslator implements Function<Historico, HistoricoDTO> {

	@Override
	public HistoricoDTO apply(Historico historico) {
		HistoricoDTO dto = new HistoricoDTO();
		dto.setToken(historico.getToken());
		dto.setArquivo(historico.getArquivo());
		dto.setStatus(historico.getStatus());
		dto.setData(historico.getData());
		dto.setQuantidadeResgitrosNovos(historico.getQuantidadeRegistrosNovos());
		dto.setQuantidadeRegistrosAlterados(historico.getQuantidadeRegistrosAlterados());
		dto.setQuantidadeRegistrosComErros(historico.getQuantidadeRegistrosComErros());
		return dto;
	}
}
