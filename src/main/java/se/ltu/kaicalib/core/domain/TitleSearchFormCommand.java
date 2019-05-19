package se.ltu.kaicalib.core.domain;

public class TitleSearchFormCommand {

    String titleSearchString;

    String dropDownSelectedValue;

    public TitleSearchFormCommand() {}

    public String getTitleSearchString() {
        return titleSearchString;
    }

    public void setTitleSearchString(String string) {
        this.titleSearchString = string;
    }
}
