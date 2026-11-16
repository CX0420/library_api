
package com.library.library_api.service;

import com.library.library_api.entity.Borrower;

import java.util.List;

public interface BorrowerService {

    List<Borrower> getAllBorrowers();

    Borrower getBorrowerById(Long id);

    Borrower createBorrower(Borrower borrower);

    Borrower updateBorrower(Long id, Borrower borrower);

    void deleteBorrower(Long id);
}
