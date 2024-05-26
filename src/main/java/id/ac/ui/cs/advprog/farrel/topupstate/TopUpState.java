package id.ac.ui.cs.advprog.farrel.topupstate;

import id.ac.ui.cs.advprog.farrel.model.TopUp;

// TopUpState.java
public interface TopUpState {
    void processSuccess(TopUp topUp);
    void processFailed(TopUp topUp);
    String getState();
}
