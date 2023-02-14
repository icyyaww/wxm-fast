package com.wxm.msfast.nostalgia.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.common.entity.LoginUser;
import com.wxm.msfast.base.common.enums.BaseUserExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.nostalgia.common.constant.Constants;
import com.wxm.msfast.nostalgia.common.enums.*;
import com.wxm.msfast.nostalgia.common.exception.UserExceptionEnum;
import com.wxm.msfast.nostalgia.common.rest.request.admin.user.UserPageRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.*;
import com.wxm.msfast.nostalgia.common.rest.request.admin.user.UserExamineRequest;
import com.wxm.msfast.nostalgia.common.rest.response.admin.user.UserPageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.front.fruser.*;
import com.wxm.msfast.nostalgia.dao.FrUserDao;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.entity.FrUserExamineEntity;
import com.wxm.msfast.nostalgia.entity.RecommendConfigEntity;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.wxm.msfast.nostalgia.service.FrUserExamineService;
import com.wxm.msfast.nostalgia.service.FrUserService;
import com.wxm.msfast.nostalgia.service.RecommendConfigService;
import com.wxm.msfast.nostalgia.service.UserMatchingService;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    UserMatchingService userMatchingService;

    @Autowired
    RecommendConfigService recommendConfigService;

    @Autowired
    FrUserExamineService frUserExamineService;

    @Autowired
    MsfFileService msfFileService;

    @Autowired
    RedissonClient redissonClient;


    @Override
    public Long countByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return count(frUserEntityWrapper);
    }

    @Override
    public FrUserEntity getFrUserByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public List<RecommendUserInfoResponse> getRecommendUserInfo(RecommendUserRequest request) {

        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        Integer num = Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name()));
        if (loginUser == null) {
            //未登陆
            if (request == null || request.getAge() == null || request.getGender() == null) {
                throw new JrsfException(UserExceptionEnum.SEARCH_PARAM_EMPTY_EXCEPTION);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("gender", request.getGender().name());
            param.put("size", num);

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.add(Calendar.YEAR, -(request.getAge() + Constants.AGE_DIFFER));
            param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.add(Calendar.YEAR, -(request.getAge() - Constants.AGE_DIFFER));
            param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));
            List<RecommendUserInfoResponse> userInfoResponse = getRecommendUserInfoByParam(param, num);
            return userInfoResponse;
        } else {
            //已登录
            FrUserEntity frUserEntity = this.getById(loginUser.getId());
            Map<String, Object> param = new HashMap<>();
            param.put("gender", frUserEntity.getGender().name());
            param.put("selfId", frUserEntity.getId());
            Integer numSize = num - Integer.valueOf(userMatchingService.matchingNum().toString());
            param.put("size", numSize);

            //默认配置信息
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(frUserEntity.getBirthday());
            calendarStart.add(Calendar.YEAR, -Constants.AGE_DIFFER);
            param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(frUserEntity.getBirthday());
            calendarEnd.add(Calendar.YEAR, Constants.AGE_DIFFER);
            param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));

            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                List<String> city = new ArrayList<>();
                city.add(frUserEntity.getCity());
                param.put("city", city);
            }

            //根据配置信息搜索
            RecommendConfigEntity recommendConfigEntity = this.recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
            if (recommendConfigEntity != null) {
                if (CollectionUtil.isNotEmpty(recommendConfigEntity.getCity())) {
                    param.put("city", recommendConfigEntity.getCity());
                }

                if (recommendConfigEntity.getMinAge() != null) {
                    Calendar calendarEndConfig = Calendar.getInstance();
                    calendarEndConfig.add(Calendar.YEAR, -recommendConfigEntity.getMinAge());
                    param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEndConfig.getTime())));
                }

                if (recommendConfigEntity.getMaxAge() != null) {
                    Calendar calendarStartConfig = Calendar.getInstance();
                    calendarStartConfig.add(Calendar.YEAR, -recommendConfigEntity.getMaxAge());
                    param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStartConfig.getTime())));
                }
            }

            return getRecommendUserInfoByParam(param, numSize);
        }
    }

    @Override
    @Transactional
    @Async
    public void saveRecommendConfig(FrUserEntity frUserEntity) {
        RecommendConfigEntity recommendConfigEntity = new RecommendConfigEntity();
        recommendConfigEntity.setUserId(frUserEntity.getId());
        if (StringUtils.isNotBlank(frUserEntity.getCity())) {
            List<String> city = new ArrayList<>();
            city.add(frUserEntity.getCity());
            recommendConfigEntity.setCity(city);
        }

        if (frUserEntity.getBirthday() != null) {
            Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
            recommendConfigEntity.setMinAge(age - Constants.AGE_DIFFER);
            recommendConfigEntity.setMaxAge(age + Constants.AGE_DIFFER);
        }
        recommendConfigService.save(recommendConfigEntity);
    }

    @Override
    public RecommendConfigResponse getRecommendConfig() {
        RecommendConfigResponse response = new RecommendConfigResponse();

        FrUserEntity frUserEntity = getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {

            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                List<String> city = new ArrayList<>();
                city.add(frUserEntity.getCity());
                response.setCity(city);
            }
            if (frUserEntity.getBirthday() != null) {
                Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
                response.setMaxAge(age + Constants.AGE_DIFFER);
                response.setMinAge(age - Constants.AGE_DIFFER);
            }
        }

        RecommendConfigEntity recommendConfigEntity = recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
        if (recommendConfigEntity != null) {
            BeanUtils.copyProperties(recommendConfigEntity, response);
        }
        return response;
    }

    @Override
    @Transactional
    public void updateConfigInfo(RecommendConfigRequest request) {

        if (request.getMinAge().compareTo(request.getMaxAge()) > 0) {
            throw new JrsfException(UserExceptionEnum.MIN_AGE_GREATER_EXCEPTION);
        }
        RecommendConfigEntity recommendConfigEntity = recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
        if (recommendConfigEntity == null) {
            recommendConfigEntity = new RecommendConfigEntity();
            recommendConfigEntity.setUserId(TokenUtils.getOwnerId());
        }

        BeanUtils.copyProperties(request, recommendConfigEntity);
        recommendConfigService.saveOrUpdate(recommendConfigEntity);
    }

    @Override
    public void updateLatelyTime(Integer userId) {

        this.update(null, new LambdaUpdateWrapper<FrUserEntity>()
                .set(FrUserEntity::getLatelyTime, new Date())
                .eq(FrUserEntity::getId, userId)
        );
    }

    @Override
    public PersonalCenterResponse getPersonalCenter() {

        PersonalCenterResponse response = new PersonalCenterResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            if (frUserEntity.getAdditional() != null) {
                BeanUtils.copyProperties(frUserEntity.getAdditional(), response);
            }

            response.setCompletionRatio(getRatio(frUserEntity));
            LikeResponse likeResponse = this.baseMapper.getPersonalLike(frUserEntity.getId());
            if (likeResponse != null) {
                BeanUtils.copyProperties(likeResponse, response);
                if (likeResponse.getLikeMe() > 0) {
                    LambdaQueryWrapper<UserMatchingEntity> queryWrapper = new QueryWrapper<UserMatchingEntity>().lambda()
                            .eq(UserMatchingEntity::getOtherUser, frUserEntity.getId())
                            .eq(UserMatchingEntity::getResult, true)
                            .orderByDesc(UserMatchingEntity::getId)
                            .last("limit 1");
                    UserMatchingEntity userMatchingEntity = this.userMatchingService.getBaseMapper().selectOne(queryWrapper);
                    if (userMatchingEntity != null) {
                        FrUserEntity likeMe = this.getById(userMatchingEntity.getUserId());
                        if (likeMe != null) {
                            response.setLikeMeheadPortrait(likeMe.getHeadPortrait());
                        }
                    }
                }
            }

        }
        return response;
    }

    @Override
    public PersonalInfoResponse personalInfo() {


        PersonalInfoResponse personalInfo = new PersonalInfoResponse();
        Integer ownerId = TokenUtils.getOwnerId();
        FrUserEntity frUserEntity = this.getById(ownerId);
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, personalInfo);
            if (frUserEntity.getAdditional() != null) {
                BeanUtils.copyProperties(frUserEntity.getAdditional(), personalInfo);
            }
            if (frUserEntity.getBirthday() != null) {
                personalInfo.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
                personalInfo.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
            }

            if (AuthStatusEnum.EXAMINE.equals(frUserEntity.getAuthStatus())) {
                personalInfo.setRemarks(AuthStatusEnum.EXAMINE.getDesc());
            } else if (AuthStatusEnum.REFUSE.equals(frUserEntity.getAuthStatus())) {
                LambdaQueryWrapper<FrUserExamineEntity> queryWrapper = new QueryWrapper<FrUserExamineEntity>().lambda()
                        .eq(FrUserExamineEntity::getUserId, ownerId)
                        .eq(FrUserExamineEntity::getAuthType, AuthTypeEnum.InfoAuth)
                        .eq(FrUserExamineEntity::getAuthStatus, AuthStatusEnum.REFUSE)
                        .orderByDesc(FrUserExamineEntity::getId)
                        .last("limit 1");
                FrUserExamineEntity userExamineEntity = frUserExamineService.getOne(queryWrapper);
                if (userExamineEntity != null) {
                    personalInfo.setRemarks(userExamineEntity.getRemarks());
                }
            }


        }

        return personalInfo;
    }

    @Override
    @Transactional
    public void photoEdit(PhotoEditRequest request) {
        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            if (PhotoEditTypeEnum.DELETE.equals(request.getPhotoEditType())) {

                if (StringUtils.isBlank(request.getOldUrl())) {
                    throw new JrsfException(UserExceptionEnum.OLD_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {

                    if (frUserEntity.getWaitApprovedImg().get(0).equals(request.getOldUrl())) {
                        throw new JrsfException(UserExceptionEnum.FIRST_PHOTO_NOT_DELETE_EXCEPTION);
                    }

                    frUserEntity.getWaitApprovedImg().removeIf(p -> p.equals(request.getOldUrl()));
                    if (CollectionUtil.isEmpty(frUserEntity.getImgList()) || !frUserEntity.getImgList().contains(request.getOldUrl())) {
                        msfFileService.deleteFileByUrl(request.getOldUrl());
                    }

                }
            } else if (PhotoEditTypeEnum.REPLACE.equals(request.getPhotoEditType())) {
                if (StringUtils.isBlank(request.getNewUrl())) {
                    throw new JrsfException(UserExceptionEnum.NEW_URL_NOT_EMPTY_EXCEPTION);
                }

                if (StringUtils.isBlank(request.getOldUrl())) {
                    throw new JrsfException(UserExceptionEnum.OLD_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {
                    Collections.replaceAll(frUserEntity.getWaitApprovedImg(), request.getOldUrl(), request.getNewUrl());
                    if (CollectionUtil.isEmpty(frUserEntity.getImgList()) || !frUserEntity.getImgList().contains(request.getOldUrl())) {
                        msfFileService.deleteFileByUrl(request.getOldUrl());
                    }

                    setUserAuth(frUserEntity,AuthStatusEnum.EXAMINE);
                }
            } else if (PhotoEditTypeEnum.ADD.equals(request.getPhotoEditType())) {

                if (StringUtils.isBlank(request.getNewUrl())) {
                    throw new JrsfException(UserExceptionEnum.NEW_URL_NOT_EMPTY_EXCEPTION);
                }

                if (CollectionUtil.isNotEmpty(frUserEntity.getWaitApprovedImg())) {
                    frUserEntity.getWaitApprovedImg().add(request.getNewUrl());
                } else {
                    List<String> urlList = new ArrayList<>();
                    urlList.add(request.getNewUrl());
                    frUserEntity.setWaitApprovedImg(urlList);
                }
                setUserAuth(frUserEntity,AuthStatusEnum.EXAMINE);
            }

            this.updateById(frUserEntity);

        } finally {
            lock.unlock();
        }
    }

    private void setUserAuth(FrUserEntity frUserEntity, AuthStatusEnum examine) {

        if (frUserEntity.getAdditional() == null) {
            AdditionalResponse additional = new AdditionalResponse();
            additional.setWaitApprovedStatus(examine);
            frUserEntity.setAdditional(additional);
        } else {
            frUserEntity.getAdditional().setWaitApprovedStatus(examine);
        }
    }

    @Override
    public BaseInfoResponse baseInfo() {
        BaseInfoResponse response = new BaseInfoResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            BeanUtils.copyProperties(frUserEntity, response);
            response.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
        }
        return response;
    }

    @Override
    public void baseInfoEdit(BaseInfoEditRequest request) {

        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
            if (frUserEntity != null) {
                BeanUtils.copyProperties(request, frUserEntity);
                frUserEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
                this.updateById(frUserEntity);
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void characterEdit(CharacterRequest request) {
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {
            CharacterTypeResponse characterType = new CharacterTypeResponse();

            characterType.setExtroversionScore(getCharacter(request.getExtroversionScore()));
            characterType.setImagineScore(getCharacter(request.getImagineScore()));
            characterType.setPerceptualScore(getCharacter(request.getPerceptualScore()));
            characterType.setPlanScore(getCharacter(request.getPlanScore()));

            characterType.setName(getCharacterName(characterType));

            frUserEntity.setCharacterType(characterType);
            this.updateById(frUserEntity);
        }
    }

    @Override
    public DoubleAuthResponse doubleAuth() {
        DoubleAuthResponse response = new DoubleAuthResponse();
        FrUserEntity frUserEntity = this.getById(TokenUtils.getOwnerId());
        if (frUserEntity != null && frUserEntity.getAdditional() != null) {
            response.setIdentityAuth(frUserEntity.getAdditional().getIdentityAuth());
            response.setEducationAuth(frUserEntity.getAdditional().getEducationAuth());
        }
        return response;
    }

    @Override
    public UserInfoResponse userInfo(Integer id) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        FrUserEntity frUserEntity = this.getById(id);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }

        BeanUtils.copyProperties(frUserEntity, userInfoResponse);
        if (frUserEntity.getAdditional() != null) {
            BeanUtils.copyProperties(frUserEntity.getAdditional(), userInfoResponse);
        }
        if (frUserEntity.getBirthday() != null) {
            userInfoResponse.setConstellation(DateUtils.getConstellation(frUserEntity.getBirthday()));
            userInfoResponse.setAge(DateUtils.getAgeByBirth(frUserEntity.getBirthday()));
        }
        Integer ownerId = TokenUtils.getOwnerId();

        Wrapper<UserMatchingEntity> queryWrapper = new QueryWrapper<UserMatchingEntity>()
                .lambda()
                .eq(UserMatchingEntity::getUserId, ownerId)
                .eq(UserMatchingEntity::getOtherUser, id);

        UserMatchingEntity userMatchingEntity = userMatchingService.getBaseMapper().selectOne(queryWrapper);
        if (userMatchingEntity != null) {
            if (userMatchingEntity.getResult()) {
                Wrapper<UserMatchingEntity> queryWrapperOther = new QueryWrapper<UserMatchingEntity>()
                        .lambda()
                        .eq(UserMatchingEntity::getUserId, id)
                        .eq(UserMatchingEntity::getOtherUser, ownerId);
                UserMatchingEntity userMatchingEntityOther = userMatchingService.getBaseMapper().selectOne(queryWrapperOther);
                if (userMatchingEntityOther == null) {
                    userInfoResponse.setMatchingStatus(MatchingStatusEnum.LIKE);
                } else {
                    if (userMatchingEntityOther.getResult()) {
                        userInfoResponse.setMatchingStatus(MatchingStatusEnum.SUCCESS);
                    } else {
                        userInfoResponse.setMatchingStatus(MatchingStatusEnum.NOT_LIKE_ME);

                    }
                }

            } else {

                userInfoResponse.setMatchingStatus(MatchingStatusEnum.NOT_LIKE);
            }


        } else {
            userInfoResponse.setMatchingStatus(MatchingStatusEnum.NULL);
        }
        return userInfoResponse;
    }

    @Override
    @Transactional
    public void examine(UserExamineRequest request) {

        RLock lock = redissonClient.getLock(Constants.PHOTO_EDIT + request.getUserId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = this.getById(request.getUserId());
            if (frUserEntity == null) {
                throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }

            if (frUserEntity.getVersion() != request.getVersion()) {
                throw new JrsfException(UserExceptionEnum.USER_VERSION_DIFFERENT_EXCEPTION);
            }

            if (AuthStatusEnum.PASS.equals(request.getResult())) {
                frUserEntity.setAuthStatus(request.getResult());
                //复制图片
                List<String> imgList = frUserEntity.getImgList();
                frUserEntity.setImgList(frUserEntity.getWaitApprovedImg());
                msfFileService.deleteImg(imgList, frUserEntity.getImgList());
            }

            if (frUserEntity.getAdditional() == null) {
                AdditionalResponse additional = new AdditionalResponse();
                additional.setWaitApprovedStatus(request.getResult());
                frUserEntity.setAdditional(additional);
            } else {
                frUserEntity.getAdditional().setWaitApprovedStatus(request.getResult());
            }

            this.updateById(frUserEntity);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public PageResult<UserPageResponse> examinePage(UserPageRequest request, Integer pageIndex, Integer pageSize) {

        Page<UserPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.baseMapper.getExaminePage(request);
        PageResult<UserPageResponse> result = new PageResult<>(page);
        return result;
    }


    private String getCharacterName(CharacterTypeResponse characterType) {
        String name = "";
        if (characterType != null) {
            if (characterType.getExtroversionScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getExtroversionScore().getPosition()) ? "E" : "I");
            }

            if (characterType.getImagineScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getImagineScore().getPosition()) ? "N" : "S");
            }

            if (characterType.getPerceptualScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getPerceptualScore().getPosition()) ? "F" : "T");
            }

            if (characterType.getPlanScore() != null) {
                name += (CharacterPosition.LEFT.equals(characterType.getPlanScore().getPosition()) ? "J" : "P");
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("INTP", "思绪飞扬的学者");
        map.put("INTJ", "独立自主的专家");
        map.put("ENTP", "大雄的机器猫");
        map.put("ENTJ", "天生的领导者");
        map.put("ENFJ", "谆谆教诲的教育家");
        map.put("INFJ", "精神世界的引路人");
        map.put("INFP", "完美主义的知心人");
        map.put("ENFP", "追梦人");
        map.put("ESFP", "不可或缺的大活宝");
        map.put("ISFP", "静美的艺术家");
        map.put("ISTP", "谦逊的手艺人");
        map.put("ESTP", "活在当下的践行者");
        map.put("ISFJ", "具奉献精神的保护者");
        map.put("ISTJ", "一丝不苟的检查者");
        map.put("ESFJ", "盛情难却的东道主");
        map.put("ESTJ", "天生的管理者");

        name += ("-" + map.get(name));
        return name;
    }


    private CharacterResponse getCharacter(Integer score) {

        CharacterResponse characterResponse = new CharacterResponse();
        characterResponse.setScore(score);
        characterResponse.setPosition(score >= 5 ? CharacterPosition.LEFT : CharacterPosition.RIGHT);
        return characterResponse;

    }

    private Integer getRatio(FrUserEntity frUserEntity) {

        BigDecimal total = new BigDecimal("5");
        BigDecimal completion = new BigDecimal("0");

        if (frUserEntity.getMarriage() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getLoveGoal() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getEmotional() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getAnnualSalary() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        if (frUserEntity.getCharacterType() != null) {
            completion = completion.add(new BigDecimal("1"));
        }

        return completion.divide(total).multiply(new BigDecimal("100")).setScale(0, RoundingMode.DOWN).intValue();
    }

    private List<RecommendUserInfoResponse> getRecommendUserInfoByParam(Map<String, Object> param, Integer num) {

        Integer max = 2;
        //todo 测试为2 正式时为10
        if (num > max) {
            param.put("size", max);
        }
        List<RecommendUserInfoResponse> list = this.baseMapper.getRecommendUserInfo(param);
        AtomicReference<Integer> numAt = new AtomicReference<>(num);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(model -> {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
                model.setConstellation(DateUtils.getConstellation(model.getBirthday()));
                model.setSurplusNum(numAt.getAndSet(numAt.get() - 1));
            });
        }
        return list;
    }
}
