package se.ltu.kaicalib.core.domain.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * Receipt keeps a list of loans to then be used
 * as feedback for a user. Receipt does not need to be persisted.
 */
public class Receipt {

    private Patron patron; // Not really relevant as receipts are not stored anyhow.
    private List<Loan> loanlist;
    private List<Loan> renewedLoanList;


    public Receipt(Patron patron) {
        this.patron = patron;
        this.loanlist = new ArrayList<>();
        this.renewedLoanList = new ArrayList<>();
    }


    public void addLoan(Loan loan) {
        this.loanlist.add(loan);
    }


    public void addRenewedLoan(Loan loan) {
        this.renewedLoanList.add(loan);
    }


    public int gettotalLoanCount() {
        return loanlist.size() + renewedLoanList.size();
    }

    public List<Loan> getLoanlist() {
        return loanlist;
    }

    public List<Loan> getRenewedLoanList() {
        return renewedLoanList;
    }
}
