package tn.esprit.epione.persistence;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import tn.esprit.epione.enumerations.AccountType;

@Entity
@DiscriminatorValue(value = "administrator")
public class Administrator extends User {

	public Administrator() {
		this.setRole(AccountType.administrator);
	}

}
