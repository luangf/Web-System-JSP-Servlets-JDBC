package beandto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BeanDTOGraficoSalarioUser implements Serializable {

	private static final long serialVersionUID = 1L;

	List<Double> medias_salariais = new ArrayList<Double>();
	List<String> perfils = new ArrayList<String>();

	public List<Double> getMedias_salariais() {
		return medias_salariais;
	}

	public void setMedias_salariais(List<Double> medias_salariais) {
		this.medias_salariais = medias_salariais;
	}

	public List<String> getPerfils() {
		return perfils;
	}

	public void setPerfils(List<String> perfils) {
		this.perfils = perfils;
	}

}
