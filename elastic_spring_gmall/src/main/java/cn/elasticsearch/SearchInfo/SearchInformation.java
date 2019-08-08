package cn.elasticsearch.SearchInfo;

public class SearchInformation {

    private String key;

    //    @Value("goods")
    private String index = "goods";

    // @Value("true")
    private boolean asc = true;

    // @Value("1")
    private Integer low = 1;

    // @Value("9999")
    private Integer hight = Integer.MAX_VALUE;

    @Override
    public String toString() {
        return "key=" + key + ", index=" + index + ", asc=" + asc + ", low=" + low + ", hight=" + hight;
    }

    public boolean isAsc() {
        return asc;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getHight() {
        return hight;
    }

    public void setHight(Integer hight) {
        this.hight = hight;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean getAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}


