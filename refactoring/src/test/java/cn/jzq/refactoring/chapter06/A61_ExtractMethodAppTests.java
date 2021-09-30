package cn.jzq.refactoring.chapter06;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.jzq.fightingcommon.uitls.R;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class A61_ExtractMethodAppTests {


    @Test
    public void test() {
        // given
        A61_ExtractMethodApp extractMethodApp = new A61_ExtractMethodApp();
        // when
        R<JSONObject> r1 = extractMethodApp.getBill();
        R<JSONObject> r2 = extractMethodApp.getBillUsingExtractMethod();
        // then
        Assert.assertNotNull(r2);
        JSONArray detailList = r2.getData().getJSONArray("detailList");
        Assert.assertFalse(detailList.isEmpty());
        Assert.assertEquals(r1.getData().getJSONArray("detailList"), detailList);

    }
}
