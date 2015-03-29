package apinetflix.pojo;

public class Ad {
    private String body;
    private Boolean companyAd;
    private int listId;
    private String subject;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getCompanyAd() {
        return companyAd;
    }

    public void setCompanyAd(Boolean companyAd) {
        this.companyAd = companyAd;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "body='" + body + '\'' +
                ", companyAd=" + companyAd +
                ", listId=" + listId +
                ", subject='" + subject + '\'' +
                '}';
    }
}
