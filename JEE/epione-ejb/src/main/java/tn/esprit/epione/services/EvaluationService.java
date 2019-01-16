package tn.esprit.epione.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import tn.esprit.epione.interfaces.EvaluationServiceLocal;
import tn.esprit.epione.persistence.Appointment;
import tn.esprit.epione.persistence.Evaluation;

@LocalBean
@Stateless
public class EvaluationService implements EvaluationServiceLocal {

	@PersistenceContext(name = "epione-ejb")
	EntityManager em;

	@Override
	public int addEvalutation(Evaluation evaluation) {
		try {
			em.persist(evaluation);
			em.flush();
			return evaluation.getId();
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int editEvalutation(Evaluation evaluation) {
		try {
			em.merge(evaluation);
			return 1;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	@Override
	public int deleteEvalutation(Evaluation evaluation) {
		try {
			Evaluation delete = em.find(Evaluation.class, evaluation.getId());
			em.remove(delete);
			return 1;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	@Override
	public double AverageRating(int idDoctor) {
		try {
			List<Appointment> appointments = new ArrayList<>();
			TypedQuery<Appointment> query = em.createQuery("select a from Appointment a where a.doctor.id = :idDoctor",
					Appointment.class);
			query.setParameter("idDoctor",idDoctor);
			appointments = query.getResultList();
			double average = appointments.stream().mapToDouble(a -> a.getEvaluation().getNote()).average().getAsDouble();
			return average;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
