package id.ac.ui.cs.advprog.farrel.topupstate;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;

public class FailedState implements TopUpState{

    @Override
    public void processSuccess(TopUp topUp) {
    }

    @Override
    public void processFailed(TopUp topUp) {
    }
    public String getState() {
        return TopUpStatus.FAILED.getValue();
    }
}
