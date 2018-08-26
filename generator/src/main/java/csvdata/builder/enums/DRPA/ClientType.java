package csvdata.builder.enums.DRPA;

public enum ClientType {
    LEGAL_ENTITY("Организация"),
    BUSINESS_PERSON_ENTITY("Индивидуальный предприниматель");

    private final String text;

    /**
     * @param text
     */
    ClientType(final String text) {
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
