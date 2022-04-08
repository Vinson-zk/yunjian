/**
 * 
 */
package com.zk.sys.auth.service;
 
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import com.zk.base.entity.ZKBaseEntity;
import com.zk.sys.auth.entity.ZKSysAuthUser;
import com.zk.sys.helper.ZKSysTestHelper;

/**
 * ZKSysAuthUserServiceTest
 * @author 
 * @version 
 */
public class ZKSysAuthUserServiceTest {

	ZKSysAuthUser makeNew() {
        ZKSysAuthUser e = new ZKSysAuthUser();
        // e.set
        return e;
    }
	
	@Test
    public void testDml() {
	
        ZKSysAuthUserService s = ZKSysTestHelper.getMainCtx().getBean(ZKSysAuthUserService.class);

        List<ZKSysAuthUser> dels = new ArrayList<>();

        try {
            ZKSysAuthUser e = null;
            int result = 0;

            /*** 保存 ***/
            e = this.makeNew();
            result = 0;
            result = s.save(e);
            TestCase.assertEquals(1, result);
            dels.add(e);

            /*** 修改 ***/
            result = 0;
            result = s.save(e);
            TestCase.assertEquals(1, result);

            /*** 查询 ***/
            e = s.get(e);
            TestCase.assertNotNull(e);

            /*** 删除 ***/
            result = 0;
            result = s.del(e);
            TestCase.assertEquals(1, result);
            e = s.get(e);
            TestCase.assertEquals(ZKBaseEntity.DEL_FLAG.delete, e.getDelFlag().intValue());

        }
        catch(Exception e) {
            e.printStackTrace();
            TestCase.assertTrue(false);
        } finally {
            dels.forEach(item -> {
                s.diskDel(item);
            });
        }
    }
}