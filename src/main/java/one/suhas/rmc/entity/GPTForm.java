package one.suhas.rmc.entity;

public class GPTForm {
    private String classId;
    private String pros;
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
