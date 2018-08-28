package csvdata.builder.enums;

public enum  SubjectType {
    BORROWER("Заемщик"),
    LEGAL_ENTITY("ЮЛ"),
    BUSINES_PERSON_ENTITY("ИП"),
    PERSON_ENTITY("ФЛ");

    private final String text;

    /**
     * @param text
     */
    SubjectType(final String text) {
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
