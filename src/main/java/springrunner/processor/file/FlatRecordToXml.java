package springrunner.processor.file;

import springrunner.processor.AbstractProcessor;


/**
 * A processor that transform flat text record structure to well xml document.
 * 
 * <p>
 * It default recordSep = "\n" and fieldSep = "\\|", which are regex based.
 * 
 * <p>
 * Example from:
 * 
 * <pre>
 *  one|two
 *  three|four
 * </pre>
 * 
 * It will convert to:
 * 
 * <pre>
 * 
 * <data>
 *   <record>
 *   	<field>one</field>
 *   	<field>two</field>
 *   </record>
 *   	<field>three</field>
 *   	<field>four</field>
 *   </record>
 * </data>
 * 
 * </pre>
 * 
 * @author Zemian Deng
 */
public class FlatRecordToXml extends AbstractProcessor<String, String> {

	private String recordSep = "\r{0,1}\n"; // \r is to handle windows line ending.
	private String fieldSep = "\\|";

	@Override
	public String process(String text) {
		logger.debug("Converting flat to xml");
		StringBuilder sb = new StringBuilder();
		sb.append("<data>\n");
		String[] records = text.split(recordSep);
		for (String record : records) {
			sb.append("  <record>\n");
			String[] fields = record.split(fieldSep);
			for (String field : fields) {
				sb.append("    <field>" + field + "</field>\n");
			}
			sb.append("  </record>\n");
		}
		sb.append("</data>");
		return sb.toString();
	}

	public void setRecordSep(String recordSep) {
		this.recordSep = recordSep;
	}

	public void setFieldSep(String fieldSep) {
		this.fieldSep = fieldSep;
	}
}
