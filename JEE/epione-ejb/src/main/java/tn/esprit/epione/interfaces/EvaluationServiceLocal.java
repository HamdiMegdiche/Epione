package tn.esprit.epione.interfaces;

import javax.ejb.Local;

import tn.esprit.epione.persistence.Evaluation;

@Local
public interface EvaluationServiceLocal {
	public int addEvalutation(Evaluation evaluation );
	public int editEvalutation(Evaluation evaluation );
	public int deleteEvalutation(Evaluation evaluation);
	public double AverageRating(int idDoctor);
}
