package rog.domain.enumeration;

public enum StatusOfSending {
    CONFIRMED_PURPOSES, REJECTED_PURPOSES, UNCHECKED_PURPOSES, UNCHECKED_PLAN, CONFIRMED_PLAN, REJECTED_PLAN;

    public boolean isUncheckedPlan() {
        return this == UNCHECKED_PLAN;
    }

    public boolean isRejectedPlan() {
        return this == REJECTED_PLAN;
    }

    public boolean isConfirmedPlan() {
        return this == CONFIRMED_PLAN;
    }

    public boolean isConfirmedPurposes() {
        return this == CONFIRMED_PURPOSES;
    }
}
