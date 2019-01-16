package tn.esprit.epione.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.AnalyticsServiceLocal;
import tn.esprit.epione.interfaces.AnalyticsServiceRemote;



@Stateless
public class AnalyticsService implements AnalyticsServiceLocal, AnalyticsServiceRemote {

	@PersistenceContext
	private EntityManager em;

	@Override
	public long numberPatientTreated(int doctorId) {

		// Number of patient of the logged doctor who's status is treated
		TypedQuery<Long> PatientTreatedCount = em.createQuery(
				"select count(DISTINCT a.patient.id) from Appointment a where a.doctor.id=:doctorId and a.status='treated'",
				Long.class).setParameter("doctorId", doctorId);

		return Long.valueOf(PatientTreatedCount.getSingleResult());
	}

	@Override
	public double percentageCancellation(int doctorId) {

		// (Number of cancelled appointment*100) / Total number of appointment
		TypedQuery<Long> cancelledAppointmentCount = em.createQuery(
				"select count( a.patient.id) from Appointment a where a.doctor.id=:doctorId and a.status='cancelled'",
				Long.class).setParameter("doctorId", doctorId);

		TypedQuery<Long> TotalNumberAppointmentCount = em
				.createQuery("select count( a.patient.id) from Appointment a where a.doctor.id=:doctorId", Long.class)
				.setParameter("doctorId", doctorId);

		if (Long.valueOf(TotalNumberAppointmentCount.getSingleResult()) == 0)
			return 0;

		return Math.round(Long.valueOf(cancelledAppointmentCount.getSingleResult()) * 100
				/ Long.valueOf(TotalNumberAppointmentCount.getSingleResult()));
	}

	@Override
	public double percentageVacationPerOption(int doctorId, String option, String date) {

		TypedQuery<Integer> scheduleIdQuery = em
				.createQuery("select s.id from Schedule s where s.doctor.id=:doctorId", Integer.class)
				.setParameter("doctorId", doctorId);
		int scheduleId = scheduleIdQuery.getSingleResult();
		// if slot = 15min => in 1 hour there is 4 slots
		LocalDate lodate = LocalDate.parse(date);
		double openSlots = 0;
		double usedSlots = 0;
		double percentage = 0;
		TypedQuery<Object[]> q;
		TypedQuery<Long> q1;
		if (option.equals("day")) {
			// where doctor.id=doctorId
			q = em.createQuery(
					"SELECT w.startTime, w.endTime,w.doctor.id FROM WorkTime w where w.day=:dayofweek and w.schedule.id=:scheduleId",
					Object[].class).setParameter("dayofweek", lodate.getDayOfWeek().toString())
					.setParameter("scheduleId", scheduleId);
		} else if (option.equals("week")) {
			q = em.createQuery(
					"SELECT w.startTime, w.endTime,w.doctor.id FROM WorkTime w where w.schedule.id=:scheduleId",
					Object[].class).setParameter("scheduleId", scheduleId);
		} else {
			q = em.createQuery(
					"SELECT w.startTime, w.endTime,w.doctor.id FROM WorkTime w where w.schedule.id=:scheduleId",
					Object[].class).setParameter("scheduleId", scheduleId);
		}
		List<Object[]> resultList = q.getResultList();
		for (Object[] result : resultList) {
			LocalTime tempDebut = LocalTime.parse(result[0].toString());
			LocalTime tempFin = LocalTime.parse(result[1].toString());
			// calculate the difference between the startTime of the workTime and the
			// endTime
			long difference = tempDebut.until(tempFin, ChronoUnit.MINUTES);
			openSlots += difference;
		}
		System.out.println("1  :" + openSlots);
		if (option.equals("day")) {
			q1 = em.createQuery(
					"SELECT count(a) FROM Appointment a where a.doctor.id=:doctorId and a.date=:dateAppointment",
					Long.class).setParameter("dateAppointment", lodate).setParameter("doctorId", doctorId);
		} else if (option.equals("week")) {
			q1 = em.createQuery(
					"SELECT count(a) FROM Appointment a where a.doctor.id=:doctorId and a.date between :startDate and :endDate",
					Long.class).setParameter("startDate", lodate).setParameter("endDate", lodate.plusDays(7))
					.setParameter("doctorId", doctorId);
		} else {
			q1 = em.createQuery(
					"SELECT count(a) FROM Appointment a where a.doctor.id=:doctorId and a.date between :startDate and :endDate",
					Long.class).setParameter("startDate", lodate).setParameter("endDate", lodate.plusDays(30))
					.setParameter("doctorId", doctorId);
			openSlots = openSlots * 4;
		}
		if (openSlots == 0)
			return 0;
		// get slot of doctor chosen
		TypedQuery<Integer> slotQuery = em
				.createQuery("select s.slot from Schedule s where s.doctor.id=:doctorId", Integer.class)
				.setParameter("doctorId", doctorId);

		int slot = slotQuery.getSingleResult();
		openSlots = openSlots / 15;
		usedSlots = (q1.getSingleResult() * slot) / 15;
		percentage = (usedSlots / openSlots) * 100;
		return percentage;
	}

	@Override
	public Map<DayOfWeek, Integer> mostDayAppointment(int doctorId) {
		List<LocalDate> dates = new ArrayList<>();
		Query query = em.createQuery("select a.date from Appointment a");
		dates = query.getResultList();
		Map<DayOfWeek, Integer> most = mostCommon(dates);

		return most;

	}

	@Override
	public Map<LocalTime, Integer> mostTimeAppointment(int doctorId) {
		List<LocalTime> times = new ArrayList<>();
		Query query = em.createQuery("select a.startTime from Appointment a");
		times = query.getResultList();
		Map<LocalTime, Integer> most = mostCommonTime(times);

		return most;
	}

	public static Map<DayOfWeek, Integer> mostCommon(List<LocalDate> list) {
		List<DayOfWeek> result = new ArrayList<>();
		// Map<DayOfWeek, Integer> resultMap = new TreeMap(Collections.reverseOrder());
		Map<DayOfWeek, Integer> map = new TreeMap(Collections.reverseOrder().reversed());

		for (LocalDate t : list) {
			Integer val = map.get(t.getDayOfWeek());
			map.put(t.getDayOfWeek(), val == null ? 1 : val + 1);
		}
		/*
		 * System.out.println("max "); for (Entry<DayOfWeek, Integer> e :
		 * map.entrySet()) { System.out.println("first map value: " + e.getValue() +
		 * " key " + e.getKey()); Entry<DayOfWeek, Integer> max = null; for
		 * (Entry<DayOfWeek, Integer> ee : map.entrySet()) {
		 * System.out.println("second map value  : " + ee.getValue() + "  key  " +
		 * ee.getKey()); if (max == null || e.getValue() > max.getValue()) { max = ee;
		 * System.out.println("max  :  " + max.getValue() + "  key  " + max.getKey()); }
		 * // resultMap.put(max.getKey(), max.getValue()); } result.add(max.getKey()); }
		 */

		return map;
	}

	public static Map<LocalTime, Integer> mostCommonTime(List<LocalTime> list) {
		List<LocalTime> result = new ArrayList<>();
		// Map<LocalTime, Integer> resultMap = new TreeMap(Collections.reverseOrder());
		Map<LocalTime, Integer> map = new TreeMap(Collections.reverseOrder().reversed());

		for (LocalTime t : list) {
			Integer val = map.get(t);
			map.put(t, val == null ? 1 : val + 1);
		}
		/*
		 * System.out.println("max "); for (Entry<LocalTime, Integer> e :
		 * map.entrySet()) { System.out.println("first map value: " + e.getValue() +
		 * " key " + e.getKey()); Entry<LocalTime, Integer> max = null; for
		 * (Entry<LocalTime, Integer> ee : map.entrySet()) {
		 * System.out.println("second map value  : " + ee.getValue() + "  key  " +
		 * ee.getKey()); if (max == null || e.getValue() > max.getValue()) { max = ee;
		 * System.out.println("max  :  " + max.getValue() + "  key  " + max.getKey()); }
		 * // resultMap.put(max.getKey(), max.getValue()); } result.add(max.getKey()); }
		 */

		return map;
	}
}
