package ca.sfu.delta.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class Guard{

	private String guardID;
	private String location;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String name;
	private String telephone;
	private BigDecimal regularHours;
	private BigDecimal overtimeHours;
	private BigDecimal regularRate;
	private BigDecimal overtimeRate;

	public Guard() {

	}

	// Constructor and setters accept doubles instead of BigDecimals for ease of use
	// doubles shouldn't be used for currency due to inaccuracy
	public Guard(
			String name,
			double regularHours,
			double regularRate,
			double overtimeHours,
			double overtimeRate
	) throws IllegalArgumentException {
		setName(name);
		setRegularHours(regularHours);
		setRegularRate(regularRate);
		setOvertimeHours(overtimeHours);
		setOvertimeRate(overtimeRate);
	}

	public BigDecimal calculateTotalPay() {
		return (calculateRegularPay().add(calculateOvertimePay()));
	}

	public BigDecimal calculateRegularPay() {
		return (regularRate.multiply(regularHours));
	}

	public BigDecimal calculateOvertimePay() {
		return (overtimeRate.multiply(overtimeHours));
	}

	// Getters and setters -------------------------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("String must be non-null & non-empty");
		}
	}

	public String getGuardID() {
		return guardID;
	}

	public void setGuardID(String guardID) throws IllegalArgumentException {
		if (guardID != null) {
			this.guardID = guardID;
		} else {
			throw new IllegalArgumentException("ID must be non-null");
		}
	}

	public BigDecimal getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(double regularHours) throws IllegalArgumentException {
		BigDecimal newHours = BigDecimal.valueOf(regularHours);
		if (newHours.compareTo(BigDecimal.ZERO) >= 0) {
			this.regularHours = newHours;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public BigDecimal getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(double overtimeHours) throws IllegalArgumentException {
		BigDecimal newHours = BigDecimal.valueOf(overtimeHours);
		if (newHours.compareTo(BigDecimal.ZERO) >= 0) {
			this.overtimeHours = newHours;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public BigDecimal getRegularRate() {
		return regularRate;
	}

	public void setRegularRate(double regularRate) throws IllegalArgumentException {
		BigDecimal newRate = BigDecimal.valueOf(regularRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.regularRate = newRate;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	public BigDecimal getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(double overtimeRate) throws IllegalArgumentException {
		BigDecimal newRate = BigDecimal.valueOf(overtimeRate);
		if (newRate.compareTo(BigDecimal.ZERO) >= 0) {
			this.overtimeRate = newRate;
		} else {
			throw new IllegalArgumentException("Only non-negative values are permitted");
		}
	}

	@Override
	public String toString() {
		return "Guard{" +
				"guardID='" + guardID + "\'" +
				", location='" + location + "\'" +
				", startDate='" + startDate + "\'" +
				", endDate='" + endDate + "\'" +
				", startTime='" + startTime + "\'" +
				", endTime='" + endTime + "\'" +
				", name='" + name + "\'" +
				", telephone='" + telephone + "\'" +
				", regularHours='" + regularHours + "\'" +
				", overtimeHours='" + overtimeHours + "\'" +
				", regularRate='" + regularRate + "\'" +
				", overtimeRate='" + overtimeRate + "\'}";
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getLocation() {
		return this.location;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public String getTelephone() {
		return this.telephone;
	}

}