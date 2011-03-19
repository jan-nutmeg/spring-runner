package springrunner.data.sample;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * A dummy sample of many different data types that comes with basic JDK.
 * 
 * @author Zemian Deng
 */
public class SampleType {
	private boolean booleanVal;

	private byte byteVal;

	private char charVal;

	private int intVal;

	private long longVal;

	private float floatValue;

	private double doubleVal;

	private String stringVal;

	private Object objectVal;

	private List<Object> listVal;

	private Map<String, Object> mapVal;

	private Set<Object> setVal;

	private Properties propsVal;

	private File fileVal;

	private Date dateVal;

	private TimeUnit timeUnitVal;

	// self type
	private SampleType sampleTypeVal;

	public SampleType() {
	}

	/**
	 * Getter.
	 * 
	 * @return the booleanVal - boolean
	 */
	public boolean isBooleanVal() {
		return booleanVal;
	}

	/**
	 * Setter
	 * 
	 * @param booleanVal
	 *            boolean, the booleanVal to set
	 */
	public void setBooleanVal(boolean booleanVal) {
		this.booleanVal = booleanVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the byteVal - byte
	 */
	public byte getByteVal() {
		return byteVal;
	}

	/**
	 * Setter
	 * 
	 * @param byteVal
	 *            byte, the byteVal to set
	 */
	public void setByteVal(byte byteVal) {
		this.byteVal = byteVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the charVal - char
	 */
	public char getCharVal() {
		return charVal;
	}

	/**
	 * Setter
	 * 
	 * @param charVal
	 *            char, the charVal to set
	 */
	public void setCharVal(char charVal) {
		this.charVal = charVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the intVal - int
	 */
	public int getIntVal() {
		return intVal;
	}

	/**
	 * Setter
	 * 
	 * @param intVal
	 *            int, the intVal to set
	 */
	public void setIntVal(int intVal) {
		this.intVal = intVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the longVal - long
	 */
	public long getLongVal() {
		return longVal;
	}

	/**
	 * Setter
	 * 
	 * @param longVal
	 *            long, the longVal to set
	 */
	public void setLongVal(long longVal) {
		this.longVal = longVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the floatValue - float
	 */
	public float getFloatValue() {
		return floatValue;
	}

	/**
	 * Setter
	 * 
	 * @param floatValue
	 *            float, the floatValue to set
	 */
	public void setFloatValue(float floatValue) {
		this.floatValue = floatValue;
	}

	/**
	 * Getter.
	 * 
	 * @return the doubleVal - double
	 */
	public double getDoubleVal() {
		return doubleVal;
	}

	/**
	 * Setter
	 * 
	 * @param doubleVal
	 *            double, the doubleVal to set
	 */
	public void setDoubleVal(double doubleVal) {
		this.doubleVal = doubleVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the stringVal - String
	 */
	public String getStringVal() {
		return stringVal;
	}

	/**
	 * Setter
	 * 
	 * @param stringVal
	 *            String, the stringVal to set
	 */
	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the objectVal - Object
	 */
	public Object getObjectVal() {
		return objectVal;
	}

	/**
	 * Setter
	 * 
	 * @param objectVal
	 *            Object, the objectVal to set
	 */
	public void setObjectVal(Object objectVal) {
		this.objectVal = objectVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the listVal - List<Object>
	 */
	public List<Object> getListVal() {
		return listVal;
	}

	/**
	 * Setter
	 * 
	 * @param listVal
	 *            List<Object>, the listVal to set
	 */
	public void setListVal(List<Object> listVal) {
		this.listVal = listVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the mapVal - Map<String,Object>
	 */
	public Map<String, Object> getMapVal() {
		return mapVal;
	}

	/**
	 * Setter
	 * 
	 * @param mapVal
	 *            Map<String,Object>, the mapVal to set
	 */
	public void setMapVal(Map<String, Object> mapVal) {
		this.mapVal = mapVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the setVal - Set<Object>
	 */
	public Set<Object> getSetVal() {
		return setVal;
	}

	/**
	 * Setter
	 * 
	 * @param setVal
	 *            Set<Object>, the setVal to set
	 */
	public void setSetVal(Set<Object> setVal) {
		this.setVal = setVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the propsVal - Properties
	 */
	public Properties getPropsVal() {
		return propsVal;
	}

	/**
	 * Setter
	 * 
	 * @param propsVal
	 *            Properties, the propsVal to set
	 */
	public void setPropsVal(Properties propsVal) {
		this.propsVal = propsVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the fileVal - File
	 */
	public File getFileVal() {
		return fileVal;
	}

	/**
	 * Setter
	 * 
	 * @param fileVal
	 *            File, the fileVal to set
	 */
	public void setFileVal(File fileVal) {
		this.fileVal = fileVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the dateVal - Date
	 */
	public Date getDateVal() {
		return dateVal;
	}

	/**
	 * Setter
	 * 
	 * @param dateVal
	 *            Date, the dateVal to set
	 */
	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the timeUnitVal - TimeUnit
	 */
	public TimeUnit getTimeUnitVal() {
		return timeUnitVal;
	}

	/**
	 * Setter
	 * 
	 * @param timeUnitVal
	 *            TimeUnit, the timeUnitVal to set
	 */
	public void setTimeUnitVal(TimeUnit timeUnitVal) {
		this.timeUnitVal = timeUnitVal;
	}

	/**
	 * Getter.
	 * 
	 * @return the sampleTypeVal - SampleType
	 */
	public SampleType getSampleTypeVal() {
		return sampleTypeVal;
	}

	/**
	 * Setter
	 * 
	 * @param sampleTypeVal
	 *            SampleType, the sampleTypeVal to set
	 */
	public void setSampleTypeVal(SampleType sampleTypeVal) {
		this.sampleTypeVal = sampleTypeVal;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
