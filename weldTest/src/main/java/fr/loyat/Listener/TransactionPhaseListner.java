package fr.loyat.Listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.weld.BeanManagerImpl;

import fr.loyat.ConnexionManager;

public class TransactionPhaseListner implements PhaseListener {

	private static final long serialVersionUID = 5287791699586338572L;

	@Inject EntityManager em;
	
	
	public void afterPhase(PhaseEvent event) {
		if(event.getPhaseId()==PhaseId.RENDER_RESPONSE) {
			System.out.println(event.getPhaseId());
			System.out.println(em);
			em.getTransaction().begin();
		}
	}

	public void beforePhase(PhaseEvent event) {
		if(event.getPhaseId()==PhaseId.RESTORE_VIEW) {
			System.out.println(event.getPhaseId());
			System.out.println(em);
			em.getTransaction().begin();
		}
		
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
