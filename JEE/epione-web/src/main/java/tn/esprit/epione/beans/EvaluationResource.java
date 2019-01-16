package tn.esprit.epione.beans;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import tn.esprit.epione.interfaces.EvaluationServiceLocal;
import tn.esprit.epione.persistence.Evaluation;

@Path("evaluation")
@RequestScoped
public class EvaluationResource {
	@Context
	private UriInfo uriInfo;

	@Context
	SecurityContext securityContext;

	@EJB
	EvaluationServiceLocal es;

	@POST
	@Path("addEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEvaluation(Evaluation evaluation) {
		int id = es.addEvalutation(evaluation);
		if (id > 0)
			return Response.status(Response.Status.CREATED).entity(id).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Add evaluation failed").build();

	}

	@POST
	@Path("editEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editEvaluation(Evaluation evaluation) {
		int id = es.editEvalutation(evaluation);
		if (id > 0)
			return Response.status(Response.Status.OK).entity(id).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed wrong evaluation ID").build();

	}

	@DELETE
	@Path("deleteEvaluation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteEvaluation(Evaluation evaluation) {
		int id = es.deleteEvalutation(evaluation);
		if (id > 0)
			return Response.status(Response.Status.OK).entity(id).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed wrong evaluation ID").build();

	}

	@GET
	@Path("{idDoctor}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAverage(@PathParam("idDoctor") int idDoctor) {
		double note = es.AverageRating(idDoctor);
		if (note > 0)
			return Response.status(Response.Status.OK).entity(note).build();
		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Failed wrong Doctor ID").build();

	}
}
