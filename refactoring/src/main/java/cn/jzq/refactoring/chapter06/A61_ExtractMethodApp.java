package cn.jzq.refactoring.chapter06;


import cn.hutool.json.JSONObject;
import cn.jzq.fightingcommon.uitls.R;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 提炼函数
 * <p>
 * 将一段代码放到独立的函数中，并让方法名解释该函数的用途（以它做什么来命令，而不是它怎么做来命名）
 *
 * @author jzq
 * @date 2021-07-06
 */
public class A61_ExtractMethodApp {

    private final List<BillDetail> billDetailList = listBillDetail();

    public R<JSONObject> getBill() {
        JSONObject result = new JSONObject();
        result.set("overview", printOverview());
        String detail;
        List<String> detailList = new ArrayList<>();
        for (BillDetail billDetail : billDetailList) {
            detail = "收费项: " + billDetail.getName();
            detail += ", 价格: " + billDetail.getPrice();
            detailList.add(detail);
        }
        result.set("detailList", detailList);
        return new R<>(result);
    }

    /**
     * 将获取详情封装成一个函数
     */
    public R<JSONObject> getBillUsingExtractMethod() {
        JSONObject result = new JSONObject();
        result.set("overview", printOverview());
        result.set("detailList", getBillDetail(billDetailList));
        return new R<>(result);
    }

    private List<String> getBillDetail(List<BillDetail> billDetailList) {
        List<String> detailList = new ArrayList<>();
        String detail;
        for (BillDetail billDetail : billDetailList) {
            detail = "收费项: " + billDetail.getName();
            detail += ", 价格: " + billDetail.getPrice();
            detailList.add(detail);
        }
        return detailList;
    }

    private List<BillDetail> listBillDetail() {
        List<BillDetail> billDetailList = new ArrayList<>();
        BillDetail bd1 = new BillDetail("小炒肉", "20");
        BillDetail bd2 = new BillDetail("小茄子", "10");
        billDetailList.add(bd1);
        billDetailList.add(bd2);
        return billDetailList;
    }

    private String printOverview() {
        return "用户张三消费情况";
    }
}


@Data
@AllArgsConstructor
class BillDetail {
    private String name;

    private String price;

    public static void main(String[] args) {
        final BillDetail billDetail = new BillDetail("1", "100");
        test(billDetail);
    }

    public static void test(BillDetail billDetail) {
        System.out.println(billDetail);
        billDetail = new BillDetail("2", "200");
        System.out.println(billDetail);
    }
}
