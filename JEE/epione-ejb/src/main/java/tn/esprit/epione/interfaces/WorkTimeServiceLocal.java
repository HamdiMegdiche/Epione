package tn.esprit.epione.interfaces;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import tn.esprit.epione.persistence.Schedule;
import tn.esprit.epione.persistence.WorkTime;
import tn.esprit.epione.persistence.Doctor;

@Local
public interface WorkTimeServiceLocal {

	public void AddSchedule(Schedule schedule);

	public List<Schedule> ListSchedule();

	public void AddWorkTime(WorkTime workTime);

	

	public List<WorkTime> ListWorkTime();


	public WorkTime getWorkTimeById(int id);

	public Schedule getScheduleById(int id);

	public void updateSchedule(Schedule schedule);

	public Set<WorkTime> ListWorkTimeByDocID(int id);

}
