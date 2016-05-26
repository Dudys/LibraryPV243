package cz.muni.fi.pv242.service;

import cz.muni.fi.pv242.rest.model.BorrowingCreateDTO;
import cz.muni.fi.pv242.rest.model.BorrowingDTO;

/**
 * Created by Jan Duda on 5/26/2016.
 */
public interface BorrowingService {

    BorrowingDTO createBorrowing(BorrowingCreateDTO b);
    BorrowingDTO updateBorrowing(BorrowingDTO b);
    BorrowingDTO deleteBorrowing(BorrowingDTO b);
    BorrowingDTO getBookByID(long id);
}
