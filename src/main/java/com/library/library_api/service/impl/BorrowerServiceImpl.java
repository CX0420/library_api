
package com.library.library_api.service.impl;

import com.library.library_api.entity.Borrower;
import com.library.library_api.exception.ResourceNotFoundException;
import com.library.library_api.repository.BorrowerRepository;
import com.library.library_api.service.BorrowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerServiceImpl(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    // Get all active borrowers
    @Override
    @Transactional(readOnly = true)
    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll()
                .stream()
                .filter(borrower -> "ACTIVE".equals(borrower.getStatus()))
                .toList();
    }

    // Get borrower by ID
    @Override
    @Transactional(readOnly = true)
    public Borrower getBorrowerById(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Borrower not found with ID: " + id));

        if ("INACTIVE".equals(borrower.getStatus())) {
            throw new ResourceNotFoundException("Borrower is inactive");
        }

        return borrower;
    }

    // Create a new borrower
    @Override
    public Borrower createBorrower(Borrower borrower) {
        borrower.setId(null);
        borrower.setStatus("ACTIVE");

        return borrowerRepository.save(borrower);
    }

    // Update an existing borrower
    @Override
    public Borrower updateBorrower(Long id, Borrower borrower) {
        Borrower existingBorrower = getBorrowerById(id);

        existingBorrower.setName(borrower.getName());
        existingBorrower.setEmailAddress(borrower.getEmailAddress());

        return borrowerRepository.save(existingBorrower);
    }

    // Soft delete a borrower
    @Override
    public void deleteBorrower(Long id) {
        Borrower existingBorrower = getBorrowerById(id);

        existingBorrower.setStatus("INACTIVE");

        borrowerRepository.save(existingBorrower);
    }
}
