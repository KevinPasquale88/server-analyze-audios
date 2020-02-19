package parkinson.audio.server.utils;

public class Survey {

	private Double fcr1;

	private Double fcr2;

	public Survey(Double fcr1, Double fcr2) {
		super();
		this.fcr1 = fcr1;
		this.fcr2 = fcr2;
	}

	public Double getFcr1() {
		return fcr1;
	}

	public void setFcr1(Double fcr1) {
		this.fcr1 = fcr1;
	}

	public Double getFcr2() {
		return fcr2;
	}

	public void setFcr2(Double fcr2) {
		this.fcr2 = fcr2;
	}

}
