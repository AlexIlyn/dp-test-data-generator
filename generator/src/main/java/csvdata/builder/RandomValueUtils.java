package csvdata.builder;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RandomValueUtils {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	private static Random r = new Random();
	public static final String AGR_CRED_TYPE_EXCLUDE_1010 = "-1010";
	public static final String EKS_AGR_CRED_TYPE_ID_EXCLUDE = "-1007";
	public static final List<String> AGR_CRED_STTS_TYPE_ID_EXCLUDE = Arrays.asList(null, "", "-1", "36206", "74006",
		"60506", "74106", "5906", "33506", "45406", "49806", "44706", "47006", "42606", "45306", "47506", "74406",
		"74306", "49306", "36106", "76406", "76906", "74206", "75906", "44006", "55206", "70906", "75406", "38806",
		"74806", "55306", "76806", "52106", "45606", "45206", "51106", "82806", "44806", "66606", "47706", "34006",
		"80806", "35206", "51206", "76606", "64206", "46506", "53206", "36506", "37306", "78806");

	private static final int LEGAL_ENTITY = 0;
	public static final int BUSINESS_PERSON = 1;
	public static final int PERSON_ENTITY = 2;
	public static final String[] CUST_TYPE_CD_VARIANTS = new String[]{"Организация", "Индивидуальный предприниматель", "Физическое лицо"};

	public static Faker faker = new Faker(new Locale("ru"));

	public static String getRandomFormatedDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		Date randomDate = getRandomDate();
		String formatedDate = dateFormat.format(randomDate);
		
		return formatedDate;
	}

	public static Date getRandomDate() {

		Date randomDate;

		GregorianCalendar gc = new GregorianCalendar();

		int year = getRandomIntInRange(1960, 2018);

		gc.set(Calendar.YEAR, year);

		int dayOfYear = getRandomIntInRange(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		randomDate = gc.getTime();
		return randomDate;
	}

	public static Integer getRandomIntInRange(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return r.nextInt((max - min) + 1) + min;
	}

	public static double getRandomDoubleInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return min + (max - min) * r.nextDouble();
	}

	public static String getRandomFlag() {
		Boolean bool = r.nextBoolean();
		return bool ? "Y" : "N";
	}

	public static String getRandomAGR_CRED_STTS_TYPE_ID(){
		int rnd_id = getRandomIntInRange(0, AGR_CRED_STTS_TYPE_ID_EXCLUDE.size());
		return AGR_CRED_STTS_TYPE_ID_EXCLUDE.get(rnd_id);
	}
	public static String getRandomStringOfLength(int length) {
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		return generatedString.toUpperCase();
	}
	public static String getRandomNumberOfLength(int length) {
		boolean useLetters = false;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		return generatedString.toUpperCase();
	}

	public static String getRandomValueByValueType(ValueType valueType) {
		String result;
		switch (valueType) {
			case AGRNUM:
				return getRandomIntInRange((int) Math.pow(10, 7), (int) Math.pow(10, 8) - 1) + "/" + getRandomIntInRange((int) Math.pow(10, 7), (int) Math.pow(10, 8) - 1);
			case DATE:
				return getRandomFormatedDate(YYYY_MM_DD);
			case DATE_JOINED:
				return getRandomFormatedDate(YYYYMMDD);
			case FLAG:
				return getRandomFlag();
			case BYTE:
				return Integer.toString(getRandomIntInRange(0, Byte.MAX_VALUE));
			case SHORT:
				Integer.toString(getRandomIntInRange(0, Short.MAX_VALUE));
			case TINYINT:
				return Integer.toString(getRandomIntInRange(0, 20));
			case SMALLINT:
				return Integer.toString(getRandomIntInRange(0, (int) Math.pow(2, 16)));
			case BIGINT:
				return Integer.toString(getRandomIntInRange((int) Math.pow(10, 9), (int) Math.pow(10, 10) - 1));
			case DECIMAL:
				return Double.toString(getRandomDoubleInRange(0, Double.MAX_VALUE));
			case CURRENCY_ID:
				return Integer.toString(37107);
			case CURRENCY_CD:
				return Integer.toString(810);
			case AGR_CRED_STTS_TYPE_ID:
				return Integer.toString(getRandomIntInRange(80000, 200000));
			//TODO return getRandomAGR_CRED_STTS_TYPE_ID();
			case CUST_ID:
				return Integer.toString(getRandomIntInRange(0, Integer.MAX_VALUE - 1));
			case COMPANY_NAME:
				result = faker.company().name();
				while (result.contains("ИП")){
					result = faker.company().name();
				}
				return result;
			case PERSON_FULL_NAME:
				result = "";
				while (result.split("[\\s]").length != 3){
					result = faker.name().name();
				}
				return result;
			case CUST_TYPE_CD:
				return CUST_TYPE_CD_VARIANTS[getRandomIntInRange(0, 1)];
			case CRM_ID:
				return "1-" + getRandomStringOfLength(5);
			default:
				return null;
		}
	}
}
