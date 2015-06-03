package br.unisinos.tcc.tis4pe.wcf.outputdata;

import br.unisinos.tcc.tis4pe.wcf.ObjectiveEnum;

public class ExporterFactory {

	public static Exporter getExporterInstance(ObjectiveEnum objective){
		switch (objective) {
		case ANALISE_HISTORICA:
			return new FileExporter();
		case ANALISE_TEMPO_EXECUCAO:
			break;
		default:
			return null;
		}
		return null;
	}
}
