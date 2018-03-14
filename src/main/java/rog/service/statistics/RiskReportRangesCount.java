package rog.service.statistics;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rog.domain.PercentagesOfCalculatedValues;
import rog.domain.User;

public class RiskReportRangesCount {


	private final Logger log = LoggerFactory.getLogger(RiskReportRangesCount.class);

	private int v1;
	private int v2;
	private int v3;
	private int v4;
	private User user;
	List<PercentagesOfCalculatedValues> ranges;

	public RiskReportRangesCount(User user, List<PercentagesOfCalculatedValues> ranges) {
        this.v1 = 0;
        this.v2 = 0;
        this.v3 = 0;
        this.v4 = 0;
        this.user = user;
		this.ranges = ranges;
	}

	public int getV1() {
		return v1;
	}
	public void setV1(int v1) {
		this.v1 = v1;
	}
	public int getV3() {
		return v3;
	}
	public void setV3(int v3) {
		this.v3 = v3;
	}
	public int getV2() {
		return v2;
	}
	public void setV2(int v2) {
		this.v2 = v2;
	}
	public int getV4() {
		return v4;
	}
	public void setV4(int v4) {
		this.v4 = v4;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public void affixValues(String result) {
		final String s1 = "Good risk";
		final String s2 = "Nice risk";
		final String s3 = "Normal risk";
		final String s4 = "Bad risk";
		
		log.debug("affixValues result: " + result);
		switch(result) {
			case s1:
			    this.v1 += 1;
					break;
			case s2:
			    this.v2 += 1;
					break;
			case s3:
			    this.v3 += 1;
				break;
			case s4:
			    this.v4 += 1;
				break;
			default:
				log.debug("Problem z przyporządkowaniem");
		}

	}

	public String rangesInterpreter(int Value) {
        String result = "";
		int percentageValue = Value  * 100 / 25;

		for (PercentagesOfCalculatedValues percentagesOfCalculatedValues : ranges) {
			if(percentageValue >= percentagesOfCalculatedValues.getMin() && percentageValue <= percentagesOfCalculatedValues.getMax()) {
                result = percentagesOfCalculatedValues.getName();
//                log.debug("\n\n" + result + "\n\n");
            }
		}

		return result;
	}

	public void buildData(int value) {
		affixValues(rangesInterpreter(value));
	}


    @Override
	public String toString() {
		return "RiskReportRangesCount [v1=" + v1 + ", v2=" + v2 + ", v3=" + v3 + ", v4=" + v4 + ", userId=" + user.getId()
				+ "]";
	}

}
