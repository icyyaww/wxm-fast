import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.MsfastCommunityApplication;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.entity.SysConfigEntity;
import com.wxm.msfast.community.service.FrUserService;
import com.wxm.msfast.community.service.SysConfigService;
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
    SysConfigService sysConfigService;

    @Test
    public void test() {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, "15902393423");
        FrUserEntity frUserEntity = frUserService.getOne(frUserEntityWrapper);
        System.out.println(frUserEntity);

        Wrapper<SysConfigEntity> sysConfigEntityWrapper = new QueryWrapper<SysConfigEntity>().lambda().eq(SysConfigEntity::getCode, "");
        SysConfigEntity sysConfigEntity = sysConfigService.getOne(sysConfigEntityWrapper);
        System.out.println(sysConfigEntity);
    }
}
