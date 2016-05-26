package cz.muni.fi.pv242.service.impl;

import cz.muni.fi.pv242.persistence.BorrowingDAO;
import cz.muni.fi.pv242.persistence.entity.Borrowing;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.service.BorrowingService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public class BorrowingServiceImpl implements BorrowingService {

    @Inject
    BorrowingDAO borrowingDAO;

    private Mapper mapper = new DozerBeanMapper();

    @Override
    public BorrowingDTO createBorrowing(BorrowingCreateDTO b) {
        Borrowing borr = mapper.map(b, Borrowing.class);

        borrowingDAO.create(borr);
        return mapper.map(borr, BorrowingDTO.class);
    }

    @Override
    public BorrowingDTO updateBorrowing(BorrowingDTO b) {
        Borrowing borr = mapper.map(b, Borrowing.class);

        borrowingDAO.update(borr);
        return mapper.map(borr, BorrowingDTO.class);
    }

    @Override
    public void deleteBorrowing(BorrowingDTO b) {
        borrowingDAO.delete(mapper.map(b, Borrowing.class));
    }

    @Override
    public BorrowingDTO getBookByID(long id) {
        Borrowing borr = borrowingDAO.getById(id);

        return mapper.map(borr, BorrowingDTO.class);
    }
}
