package cz.muni.fi.pv242.rest.impl;

import cz.muni.fi.pv242.rest.RestBorrowingService;
import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;
import cz.muni.fi.pv242.service.BorrowingService;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Jan Duda on 5/26/2016.
 */
@Stateless
public class RestBorrowingServiceImpl implements RestBorrowingService {

    @Inject
    private BorrowingService borrowingService;

    @Override
    public BorrowingDTO addBorrowing(BorrowingCreateDTO borrowing) {
        return borrowingService.createBorrowing(borrowing);
    }

    @Override
    public BorrowingDTO getBorrowing(long id) {
        return borrowingService.getBorrowingByID(id);
    }

    @Override
    public BorrowingDTO updateBorrowing(BorrowingDTO updatedBorrowing) {
        return borrowingService.updateBorrowing(updatedBorrowing);
    }

    @Override
    public void deleteBorrowing(long id) {
        borrowingService.deleteBorrowing(borrowingService.getBorrowingByID(id));
    }

	@Override
	public List<BorrowingDTO> getAllBorrowings() {
		return borrowingService.getAllBorrowings();
	}
}
