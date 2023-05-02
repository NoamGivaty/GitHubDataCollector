package Models;

import java.util.List;
import java.util.Map;

public class RequestData {
    private Map<String, List<String>> reqData;

    public RequestData(Map<String, List<String>> reqData) {
        this.reqData = reqData;
    }

    public Map<String, List<String>> getReqData() {
        return reqData;
    }

    public void setReqData(Map<String, List<String>> reqData) {
        this.reqData = reqData;
    }

    public RequestData() {
    }
}
