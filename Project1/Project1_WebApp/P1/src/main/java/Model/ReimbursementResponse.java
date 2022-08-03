package Model;

import java.util.List;

/**
 * Class ReimbursementResponse
 * To generate "reimbursement" in JSON response
 */

public class ReimbursementResponse {

    private List<Reimbursement> reimbursement;

    public ReimbursementResponse() {
    }

    public void setReimbursement(List<Reimbursement> reimbursement) {
        this.reimbursement = reimbursement;
    }
}
