package tn.esprit.epione.interfaces;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

@Local
public interface AnalyticsServiceLocal {

	public long numberPatientTreated(int doctorId);

	public double percentageCancellation(int doctorId);

	public double percentageVacationPerOption(int doctorId, String option, String date);// thabet

	public Map<DayOfWeek, Integer> mostDayAppointment(int doctorId);// ne9es resultat barcha days

	public Map<LocalTime, Integer> mostTimeAppointment(int doctorId);

}
