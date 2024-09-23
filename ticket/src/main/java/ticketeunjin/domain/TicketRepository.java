package ticketeunjin.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ticketeunjin.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "tickets", path = "tickets")
public interface TicketRepository
    extends PagingAndSortingRepository<Ticket, Integer> {}
