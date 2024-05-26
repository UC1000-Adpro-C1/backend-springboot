package id.ac.ui.cs.advprog.farrel.topupstate;

import id.ac.ui.cs.advprog.farrel.enums.TopUpStatus;
import id.ac.ui.cs.advprog.farrel.model.TopUp;

public class PendingState implements TopUpState {
    @Override
    public void processSuccess(TopUp topUp) {
        topUp.setCurrentState(new SuccessState());
        topUp.setStatus(TopUpStatus.SUCCESS.getValue());
    }

    @Override
    public void processFailed(TopUp topUp) {
        topUp.setCurrentState(new FailedState());
        topUp.setStatus(TopUpStatus.FAILED.getValue());
    }

    @Override
    public String getState() {
        return TopUpStatus.PENDING.getValue();
    }
}
