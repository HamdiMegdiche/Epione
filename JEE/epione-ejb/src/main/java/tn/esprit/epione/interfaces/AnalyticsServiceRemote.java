package tn.esprit.epione.interfaces;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface AnalyticsServiceRemote {
	public long numberPatientTreated(int doctorId);

	public double percentageCancellation(int doctorId);

	public double percentageVacationPerOption(int doctorId, String option, String dateChosen);

	public Map<DayOfWeek, Integer> mostDayAppointment(int doctorId);

	public Map<LocalTime, Integer> mostTimeAppointment(int doctorId);

}
