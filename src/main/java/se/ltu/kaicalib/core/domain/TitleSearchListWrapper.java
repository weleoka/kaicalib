package se.ltu.kaicalib.core.domain;

import java.util.ArrayList;
import java.util.List;


public class TitleSearchListWrapper {

    public TitleSearchListWrapper(){}

    private ArrayList<Title> titleList;

    public List<Title> getTitleList(){
        return titleList;
    }

    public void setTitleList(ArrayList<Title> titles){
        this.titleList = titles;
    }
}
