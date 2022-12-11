import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.MsfastCommunityApplication;
import com.wxm.msfast.base.auth.entity.MsfConfigEntity;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-10 16:22
 **/

@SpringBootTest(classes = MsfastCommunityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestController {

    @Autowired
    FrUserService frUserService;

    @Autowired
    MsfConfigService msfConfigService;

    @Test
    public void test() {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, "15902393423");
        FrUserEntity frUserEntity = frUserService.getOne(frUserEntityWrapper);
        System.out.println(frUserEntity);

        Wrapper<MsfConfigEntity> sysConfigEntityWrapper = new QueryWrapper<MsfConfigEntity>().lambda().eq(MsfConfigEntity::getCode, "");
        MsfConfigEntity msfConfigEntity = msfConfigService.getOne(sysConfigEntityWrapper);
        System.out.println(msfConfigEntity);
    }
}
