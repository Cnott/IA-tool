package data;
/**
 * 
 * A Class which contains all information about a file
 * Getters and setters
 * @author deewe
 *
 */
public class FileObject {
	private String fileName;
	private String coupledFileName;
	private String cycloComplex;
	private String degOfCoupling;
	private String codeCover;
	private String sumOfCoupl;
	private String longMethod;
	private String manyParams = "-";
	
	public FileObject(String fn, String cfn, String doc) {
		fileName=fn;
		coupledFileName=cfn;
		degOfCoupling=doc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCoupledFileName() {
		return coupledFileName;
	}
	public void setCoupledFileName(String coupledFileName) {
		this.coupledFileName = coupledFileName;
	}
	public String getCycloComplex() {
		return cycloComplex;
	}
	public void setCycloComplex(String cycloComplex) {
		this.cycloComplex = cycloComplex;
	}
	public String getDegOfCoupling() {
		return degOfCoupling;
	}
	public void setDegOfCoupling(String degOfCoupling) {
		this.degOfCoupling = degOfCoupling;
	}
	public String getCodeCover() {
		return codeCover;
	}
	public void setCodeCover(String codeCover) {
		this.codeCover = codeCover;
	}
	public String getSumOfCoupl() {
		return sumOfCoupl;
	}
	public void setSumOfCoupl(String sumOfCoupl) {
		this.sumOfCoupl = sumOfCoupl;
	}
	public String getLongMethod() {
		return longMethod;
	}
	public void setLongMethod(String longMethod) {
		this.longMethod = longMethod;
	}
	public String getManyParams() {
		return manyParams;
	}
	public void setManyParams(String manyParams) {
		this.manyParams = manyParams;
	}
	
	public String toString() {
		return (fileName + ","+coupledFileName+ ","+degOfCoupling+ 
				","+cycloComplex + ","+ codeCover+ ","+ sumOfCoupl+ ","+longMethod+ ","+manyParams);
	}
}
