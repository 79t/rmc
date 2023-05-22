package one.suhas.rmc.entity;

/**
 * A form submission for the /gpt page
 */
public class GPTForm {

    /**
     * The class that the user is submitting the review for
     */
    private String classId;
    /**
     * The pros of the class
     */
    private String pros;

    /**
     * The cons of the class
     */
    private String cons;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public GPTForm(String classId, String pros, String cons) {
        this.classId = classId;
        this.pros = pros;
        this.cons = cons;
    }

    public GPTForm() {}

    @Override
    public String toString() {
        return "GPTForm{" +
                "classId=" + classId +
                ", pros='" + pros + '\'' +
                ", cons='" + cons + '\'' +
                '}';
    }
}
