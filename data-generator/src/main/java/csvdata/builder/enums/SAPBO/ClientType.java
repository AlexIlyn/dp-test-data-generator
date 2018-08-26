package csvdata.builder.enums.SAPBO;

public enum ClientType {
    LEGAL_ENTITY("Юр. лицо"),
    BUSINESS_PERSON_ENTITY("ИП");

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
