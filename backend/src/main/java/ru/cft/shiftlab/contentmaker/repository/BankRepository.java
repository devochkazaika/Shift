package ru.cft.shiftlab.contentmaker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.cft.shiftlab.contentmaker.entity.Bank;

import java.util.UUID;

public interface BankRepository extends CrudRepository<Bank, UUID> {
    @Query("SELECT b FROM Bank b WHERE b.name = :name")
    Bank findBankByName(@Param("name") String name);
}
