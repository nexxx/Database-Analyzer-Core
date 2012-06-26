package data;

/**
 * Class representing a person for the metainformation
 * 
 * @author Andreas Freitag
 */
public class Person {
  private String name;
  private String job;
  private String mail;
  private String tel;
  private String fax;

  public Person() {
	name = "";
	job = "";
	mail = "";
	tel = "";
	fax = "";
  }

  /**
   * @return the name
   */
  public String getName() {
	return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return the job
   */
  public String getJob() {
	return job;
  }

  /**
   * @param job
   *          the job to set
   */
  public void setJob(String job) {
	this.job = job;
  }

  /**
   * @return the mail
   */
  public String getMail() {
	return mail;
  }

  /**
   * @param mail
   *          the mail to set
   */
  public void setMail(String mail) {
	this.mail = mail;
  }

  /**
   * @return the tel
   */
  public String getTel() {
	return tel;
  }

  /**
   * @param tel
   *          the tel to set
   */
  public void setTel(String tel) {
	this.tel = tel;
  }

  /**
   * @return the fax
   */
  public String getFax() {
	return fax;
  }

  /**
   * @param fax
   *          the fax to set
   */
  public void setFax(String fax) {
	this.fax = fax;
  }

  @Override
  public String toString() {
	return name;
  }
}
