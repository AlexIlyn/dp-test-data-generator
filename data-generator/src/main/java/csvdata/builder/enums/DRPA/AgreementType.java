package csvdata.builder.enums.DRPA;

public enum  AgreementType {
    PLEDGE("42604"),
    COLLATERAL("48304"),
    GUARANTEE("66904");

    private final String text;

    /**
     * @param text
     */
    AgreementType(final String text) {
        this.text = text;
    }
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
